package ies.sequeros.dam.pmdm.gestionperifl.ui.components.changepasswd

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ies.sequeros.dam.pmdm.gestionperifl.application.commands.ChangePasswordCommand
import ies.sequeros.dam.pmdm.gestionperifl.application.commands.LoginCommand
import ies.sequeros.dam.pmdm.gestionperifl.application.usecases.ChangePasswordUseCase
import ies.sequeros.dam.pmdm.gestionperifl.application.usecases.LoginUseCase
import ies.sequeros.dam.pmdm.gestionperifl.infrastructure.TokenStorage
import ies.sequeros.dam.pmdm.gestionperifl.model.LoginUser
import kotlinx.coroutines.launch

class ChangePasswordViewModel(
    private val changePasswordUseCase: ChangePasswordUseCase,
) : ViewModel() {

    var oldPassword by mutableStateOf("")
    var newPassword by mutableStateOf("")
    var message by mutableStateOf("")
    var changeSuccess by mutableStateOf(false)

    fun changePassword() {
        viewModelScope.launch {
            try {
                changePasswordUseCase.change(ChangePasswordCommand(oldPassword, newPassword))
                changeSuccess = true
                message = "Contraseña cambiada"

            } catch (e: Exception) {
                message = "Error: ${e.message}"
            }
        }
    }
}