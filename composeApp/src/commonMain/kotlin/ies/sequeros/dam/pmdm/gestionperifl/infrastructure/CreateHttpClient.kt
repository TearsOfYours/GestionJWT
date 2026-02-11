package ies.sequeros.dam.pmdm.gestionperifl.infrastructure

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.auth.Auth
import io.ktor.client.plugins.auth.providers.BearerTokens
import io.ktor.client.plugins.auth.providers.bearer
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.HttpStatusCode
import io.ktor.http.contentType
import io.ktor.serialization.kotlinx.json.json


fun createHttpClient(tokenStorage: TokenStorage): HttpClient = HttpClient {
    install(ContentNegotiation) {
        json()
    }

    install(Auth) {
        bearer {
            // Cargar tokens desde el almacenamiento seguro al iniciar
            loadTokens {
                val access = tokenStorage.getAccessToken()
                val refresh = tokenStorage.getRefreshToken()
                if (!access.isNullOrEmpty() && !refresh.isNullOrEmpty()) {
                    BearerTokens(access, refresh)
                } else null
            }

            // Refrescar tokens automáticamente ante errores 401
            refreshTokens {
                try {
                    val response = client.post("http://localhost:8080/api/public/refresh") {
                        contentType(ContentType.Application.Json)
                        setBody(mapOf("refresh_token" to tokenStorage.getRefreshToken()))
                    }

                    if (response.status == HttpStatusCode.OK) {
                        val data = response.body<Map<String, String>>()
                        val newAccess = data["access_token"] ?: ""
                        val newRefresh = data["refresh_token"] ?: ""

                        // Guardar los nuevos tokens de forma persistente
                        tokenStorage.saveTokens(newAccess, newRefresh)

                        BearerTokens(newAccess, newRefresh)
                    } else {
                        // Si no se puede refrescar, limpiar almacenamiento y cerrar sesión
                        tokenStorage.clear()
                        null
                    }
                } catch (e: Exception) {
                    // En caso de error de red, limpiar tokens para evitar inconsistencias
                    tokenStorage.clear()
                    null
                }
            }

            // Evitar añadir tokens a rutas públicas
            sendWithoutRequest { request ->
                val path = request.url.pathSegments
                path.contains("public") || path.contains("auth")
            }
        }
    }
}