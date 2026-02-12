package ies.sequeros.dam.pmdm.gestionperifl.ui.components.deleteuser

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ies.sequeros.dam.pmdm.gestionperifl.application.commands.DeleteUserCommand
import ies.sequeros.dam.pmdm.gestionperifl.application.usecases.DeleteUserUseCase
import ies.sequeros.dam.pmdm.gestionperifl.ui.appsettings.AppViewModel
import kotlinx.coroutines.launch

class DeleteUserViewModel(private val useCase: DeleteUserUseCase): ViewModel() {
    var password by mutableStateOf("")
    var message by mutableStateOf("")
    var deleteSuccess by mutableStateOf(false)



    fun deleteUser() {
        viewModelScope.launch {
            try {
                useCase.delete(DeleteUserCommand(password))
                deleteSuccess = true
                message = "Usuario borrado correctamente"
            } catch (e: Exception) {
                message = "Error: ${e.message}"
            }
    }
}
}

