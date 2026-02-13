package ies.sequeros.dam.pmdm.gestionperifl.infrastructure

import ies.sequeros.dam.pmdm.gestionperifl.application.dto.UserProfileDto
import ies.sequeros.dam.pmdm.gestionperifl.model.IUserRepository
import ies.sequeros.dam.pmdm.gestionperifl.model.LoginUser
import ies.sequeros.dam.pmdm.gestionperifl.model.RegisterUser
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.plugins.ClientRequestException
import io.ktor.client.plugins.ServerResponseException
import io.ktor.client.request.*
import io.ktor.client.request.forms.formData
import io.ktor.client.request.forms.submitFormWithBinaryData
import io.ktor.client.statement.bodyAsText
import io.ktor.http.*
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json

@Serializable
data class ErrorDetail(
    val field: String,
    val message: String,
)

@Serializable
data class LoginDetailError(
    val error: String,
    val detalles: List<ErrorDetail>
)

class RestUserRepository(
    private val url: String,
    private val cliente: HttpClient,
    private val tokenStorage: TokenStorage
): IUserRepository {

    override suspend fun loginUser(user: LoginUser): Map<String, String> {
        return try {
            // Intentamos deserializar la respuesta correctamente
            val tokens = cliente.post("$url/public/login") {
                contentType(ContentType.Application.Json)
                setBody(user)
            }.body<Map<String, String>>() // Respuesta esperada de tokens

            // Guardamos los tokens de forma segura
            tokenStorage.saveTokens(
                accessToken = tokens["access_token"] ?: "",
                refreshToken = tokens["refresh_token"] ?: ""
            )

            tokens
        } catch (e: ClientRequestException) {
            // Este error se lanza cuando el servidor devuelve 4xx
            val text = e.response.bodyAsText()
            val errorResponse = try {
                Json.decodeFromString<LoginDetailError>(text)
            } catch (_: Exception) {
                null
            }

            // Lanzamos una excepción con el mensaje del servidor, si existe
            val message = errorResponse?.detalles?.joinToString { it.message } ?: text
            throw IllegalArgumentException(message)
        } catch (e: ServerResponseException) {
            // Error 5xx
            throw IllegalStateException("Error en el servidor: ${e.response.status}")
        }
    }

    override suspend fun registerUser(user: RegisterUser) {
        cliente.post("$url/public//register") {
            contentType(ContentType.Application.Json)
            setBody(user)
        }.body<Unit>()
    }


    override suspend fun logoutUser() {
        tokenStorage.clear()
    }

    override suspend fun getProfile(): UserProfileDto {
        val accessToken = tokenStorage.getAccessToken()
        val validAccessToken = try {
            accessToken?.let { token ->
                val jwt = TokenJwt(token)
                if (jwt.isSessionValid()) token else null
            }
        } catch (_: Exception) {
            null
        }

        val tokenParaUsar = validAccessToken ?: refreshTokens()

        return try {
            cliente.get("$url/users/me") {
                header(HttpHeaders.Authorization, "Bearer $tokenParaUsar")
            }.body()
        } catch (e: ClientRequestException) {
            val text = e.response.bodyAsText()
            throw IllegalStateException("Error cargando perfil: $text", e)
        } catch (e: Exception) {
            throw IllegalStateException("Error cargando perfil", e)
        }
    }

    override suspend fun updateProfileImage(
        fileBytes: ByteArray,
        filename: String
    ): UserProfileDto {
        val accessToken = tokenStorage.getAccessToken()
        val validAccessToken = try {
            accessToken?.let { token ->
                val jwt = TokenJwt(token)
                if (jwt.isSessionValid()) token else null
            }
        } catch (_: Exception) {
            null
        }

        val tokenParaUsar = validAccessToken ?: refreshTokens()

        return try {
            cliente.submitFormWithBinaryData(
                url = "$url/users/me/image",
                formData = formData {
                    append("file", fileBytes, Headers.build {
                        append(
                            HttpHeaders.ContentDisposition,
                            "form-data; name=\"file\"; filename=\"$filename\""
                        )
                        append(HttpHeaders.ContentType, ContentType.Image.Any.toString())
                    })
                }
            ) {
                method = HttpMethod.Patch
                header(HttpHeaders.Authorization, "Bearer $tokenParaUsar")
            }.body()
        } catch (e: ClientRequestException) {
            val text = e.response.bodyAsText()
            throw IllegalStateException("Error al actualizar la imagen: $text", e)
        }
    }


    override suspend fun refreshTokens(): String {
        val refreshToken = tokenStorage.getRefreshToken() ?: throw IllegalStateException("No hay refresh token")

        val response = cliente.post("$url/public/refresh") {
            contentType(ContentType.Application.Json)
            setBody(mapOf("refresh_token" to refreshToken))
        }.body<RefreshResponse>() // data class RefreshResponse(val access_token: String, val refresh_token: String)

        tokenStorage.saveTokens(response.access_token, response.refresh_token)
        return response.access_token
    }

    data class RefreshResponse(
        val access_token: String,
        val refresh_token: String
    )
}