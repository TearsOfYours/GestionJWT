package ies.sequeros.dam.pmdm.gestionperifl.infrastructure

import ies.sequeros.dam.pmdm.gestionperifl.model.ChangePasswordUser
import ies.sequeros.dam.pmdm.gestionperifl.model.DeleteUser
import ies.sequeros.dam.pmdm.gestionperifl.model.IUserRepository
import ies.sequeros.dam.pmdm.gestionperifl.model.LoginUser
import ies.sequeros.dam.pmdm.gestionperifl.model.ModifyUser
import ies.sequeros.dam.pmdm.gestionperifl.model.RegisterUser
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.plugins.ClientRequestException
import io.ktor.client.plugins.ServerResponseException
import io.ktor.client.request.*
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
    private val privateUrl: String,
    private val cliente: HttpClient,
    private val tokenStorage: TokenStorage
): IUserRepository {

    override suspend fun loginUser(user: LoginUser): Map<String, String> {
        return try {
            // Intentamos deserializar la respuesta correctamente
            val tokens = cliente.post("$url/login") {
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
        cliente.post("$url/register") {
            contentType(ContentType.Application.Json)
            setBody(user)
        }.body<Unit>()
    }

    override suspend fun logoutUser() {
        tokenStorage.clear()
    }

    override suspend fun changePassword(oldPassword: String, newPassword: String) {
        val token = tokenStorage.getAccessToken()
        try {
            cliente.put("$privateUrl/password") {
                contentType(ContentType.Application.Json)
                header("Authorization", "Bearer $token")
                setBody(ChangePasswordUser(oldPassword, newPassword))
            }.body<Unit>()
        } catch (e: ClientRequestException) {
            throw IllegalArgumentException(e.response.bodyAsText())
        } catch (e: ServerResponseException) {
            throw IllegalStateException("Error en el servidor: ${e.response.status}")
        }
    }


    override suspend fun modifyUser(name: String, status: String) {
        val token = tokenStorage.getAccessToken()
        try {
            cliente.patch("$privateUrl") {
                header("Authorization", "Bearer $token")
                contentType(ContentType.Application.Json)
                setBody(
                    ModifyUser(
                        name = name,
                        status = status
                    )
                )
            }.body<Unit>()
        } catch (e: ClientRequestException) {
            throw IllegalArgumentException(e.response.bodyAsText())
        } catch (e: ServerResponseException) {
            throw IllegalStateException("Error en el servidor: ${e.response.status}")

            }
    }

    override suspend fun deleteUser(password: String) {
        val token = tokenStorage.getAccessToken()
        try {
            cliente.delete("$privateUrl") {
                header("Authorization", "Bearer $token")
                contentType(ContentType.Application.Json)
                setBody(
                    DeleteUser(password)
                )
            }
            tokenStorage.clear()
        } catch (e: ClientRequestException) {
            throw IllegalArgumentException(e.response.bodyAsText())
        } catch (e: ServerResponseException) {
            throw IllegalStateException("Error en el servidor: ${e.response.status}")
        }

    }


}