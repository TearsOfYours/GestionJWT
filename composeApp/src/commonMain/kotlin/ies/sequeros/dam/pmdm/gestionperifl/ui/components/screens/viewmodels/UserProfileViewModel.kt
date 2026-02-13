package ies.sequeros.dam.pmdm.gestionperifl.ui.components.screens.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ies.sequeros.dam.pmdm.gestionperifl.application.dto.UserProfileDto
import ies.sequeros.dam.pmdm.gestionperifl.application.usecases.GetProfileUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class UserProfileViewModel(
    private val getProfileUseCase: GetProfileUseCase
) : ViewModel() {

    private val _state = MutableStateFlow<UserProfileDto?>(null)
    val state: StateFlow<UserProfileDto?> = _state

    init {
        loadUser()
    }

    private fun loadUser() {
        viewModelScope.launch {
            try {
                _state.value = getProfileUseCase.invoke()
            } catch (e: Exception) {
                println("Error cargando perfil: ${e.message}")
            }
        }
    }
}