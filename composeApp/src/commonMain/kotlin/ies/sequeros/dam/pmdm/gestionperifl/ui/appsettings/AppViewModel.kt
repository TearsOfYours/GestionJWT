package ies.sequeros.dam.pmdm.gestionperifl.ui.appsettings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ies.sequeros.dam.pmdm.gestionperifl.AppRoute
import ies.sequeros.dam.pmdm.gestionperifl.infrastructure.TokenJwt
import ies.sequeros.dam.pmdm.gestionperifl.infrastructure.TokenStorage
import kotlinx.coroutines.flow.MutableStateFlow

import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class AppViewModel(
    private val settings: AppSettings,
    private val tokenStorage: TokenStorage
) : ViewModel() {

    val isDarkMode = settings.isDarkMode

    // Esto es para saber a donde hay que reindicar al usuario
    private val _startDestination = MutableStateFlow<String?>(null)
    val startDestination: StateFlow<String?> = _startDestination

    init {
        checkSession()
    }

    private fun checkSession() {
        viewModelScope.launch {
            val access = tokenStorage.getAccessToken()
            val refresh = tokenStorage.getRefreshToken()

            if (!access.isNullOrEmpty()) {
                val token = TokenJwt(access)
                if (token.isSessionValid()) {
                    _startDestination.value = AppRoute.main
                } else {
                    // Opcional: podrías refrescar aquí si hay refresh token
                    _startDestination.value = AppRoute.login
                }
            } else {
                _startDestination.value = AppRoute.login
            }
        }
    }

    // Opciones de los temas
    fun toggleTheme() = settings.toggleDarkMode()
    fun setDarkMode() = settings.setDarkMode()
    fun setLightMode() = settings.setLightMode()
    fun switchMode() = settings.toggleDarkMode()
}