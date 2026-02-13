package ies.sequeros.dam.pmdm.gestionperifl.ui.components.modifyuser

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ies.sequeros.dam.pmdm.gestionperifl.application.commands.ModifyUserCommand
import ies.sequeros.dam.pmdm.gestionperifl.application.usecases.ModifyUserUseCase
import kotlinx.coroutines.launch

class ModifyUserViewModel(
    private val modifyUseCase: ModifyUserUseCase,
//    private val getProfileUseCase: GetProfileUseCase
) : ViewModel() {

    var name by mutableStateOf("")
    var status by mutableStateOf("")
    var message by mutableStateOf("")
    var isLoading by mutableStateOf(false)

    fun modifyUser() {
        viewModelScope.launch {
            try {
                isLoading = true
                modifyUseCase.modify(
                    ModifyUserCommand(name, status)
                )
                message = "Usuario modificado correctamente"
            } catch (e: Exception) {
                message = "Error: ${e.message}"
            } finally {
                isLoading = false
            }
        }
    }
}
