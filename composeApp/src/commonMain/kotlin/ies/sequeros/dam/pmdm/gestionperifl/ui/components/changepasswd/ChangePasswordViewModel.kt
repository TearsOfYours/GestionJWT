package ies.sequeros.dam.pmdm.gestionperifl.ui.components.changepasswd

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ies.sequeros.dam.pmdm.gestionperifl.application.commands.ChangePasswordCommand
import ies.sequeros.dam.pmdm.gestionperifl.application.usecases.ChangePasswordUseCase
import kotlinx.coroutines.launch

class ChangePasswordViewModel(private val useCase: ChangePasswordUseCase): ViewModel() {
    var oldPassword by mutableStateOf("")
    var newPassword by mutableStateOf("")
    var message by mutableStateOf("")

    fun changePassword() {
        viewModelScope.launch {
            try {
                useCase.change(ChangePasswordCommand(oldPassword, newPassword))
                message = "Contraseña cambiada correctamente"
            } catch (e: Exception) {
                message = "Error: ${e.message}"
            }
        }
    }
}