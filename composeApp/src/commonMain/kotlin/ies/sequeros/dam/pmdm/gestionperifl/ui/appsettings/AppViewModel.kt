package ies.sequeros.dam.pmdm.gestionperifl.ui.appsettings

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ies.sequeros.dam.pmdm.gestionperifl.AppRoute
import ies.sequeros.dam.pmdm.gestionperifl.application.dto.RefreshDto
import ies.sequeros.dam.pmdm.gestionperifl.infrastructure.TokenJwt
import ies.sequeros.dam.pmdm.gestionperifl.infrastructure.TokenStorage
import ies.sequeros.dam.pmdm.gestionperifl.infrastructure.ktor.createHttpClient
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.contentType
import kotlinx.coroutines.flow.MutableStateFlow

import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class AppViewModel(
    private val settings: AppSettings,
    private val tokenStorage: TokenStorage,
    private val client: HttpClient
) : ViewModel() {

    val isDarkMode = settings.isDarkMode

    // Esto es para saber a donde hay que reindicar al usuario
    private val _startDestination = MutableStateFlow<String?>(null)
    val startDestination: StateFlow<String?> = _startDestination
    var isLoggedIn by mutableStateOf(false)
        private set
    init {
        checkSession()
    }

    private fun checkSession() {
        viewModelScope.launch {
            isLoggedIn = true
            val access = tokenStorage.getAccessToken()
            val refresh = tokenStorage.getRefreshToken()

            if (!access.isNullOrEmpty()) {
                val token = TokenJwt(access)
                if (token.isSessionValid()) {
                    // Token válido → ir a main
                    _startDestination.value = AppRoute.main
                } else if (!refresh.isNullOrEmpty()) {
                    // Token expirado → intentar refrescar
                    isLoggedIn = false
                    val newTokens = tryRefreshToken(refresh)
                    if (newTokens != null) {
                        tokenStorage.saveTokens(newTokens.access_token!!, newTokens.refresh_token!!)
                        _startDestination.value = AppRoute.main
                    } else {
                        // No se pudo refrescar → ir a login
                        _startDestination.value = AppRoute.login
                    }
                } else {
                    _startDestination.value = AppRoute.login
                }
            } else {
                _startDestination.value = AppRoute.login
            }
        }
    }
    // Función para llamar al endpoint de refresh
    private suspend fun tryRefreshToken(refreshToken: String): RefreshDto? {
        return try {
            client.post("http://localhost:8080/api/public/refresh") {
                contentType(ContentType.Application.Json)
                setBody(mapOf("refresh_token" to refreshToken))
            }.body<RefreshDto>()
        } catch (e: Exception) {
            null
        }
    }
    fun logout() {
        tokenStorage.clear()
        isLoggedIn = false
    }
    // Opciones de los temas
    fun toggleTheme() = settings.toggleDarkMode()
    fun setDarkMode() = settings.setDarkMode()
    fun setLightMode() = settings.setLightMode()
    fun switchMode() = settings.toggleDarkMode()
}