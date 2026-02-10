package ies.sequeros.dam.pmdm.gestionperifl.ui.components.register

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ies.sequeros.dam.pmdm.gestionperifl.aplication.commands.RegisterCommand
import ies.sequeros.dam.pmdm.gestionperifl.aplication.usecases.RegisterUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class RegisterFormViewModel: ViewModel(

) {

    private val _state = MutableStateFlow(RegisterState())
    val state: StateFlow<RegisterState> = _state.asStateFlow()
    val isFormValid = MutableStateFlow(false)

    fun onUsernameChange(username: String) {
        _state.update {
            it.copy(
                username = username,
                usernameError = if (username.isBlank()) null else "Escribe un nombre de usuario")
        }
        validForm()
    }

    fun onEmailChange(email: String) {
        _state.update {
            it.copy(
                email = email,
                emailError = if (email.contains("@")) null else "Email no válido")
        }
        validForm()
    }

    fun onPasswordChange(password: String) {
        _state.update {
            it.copy(
                password = password,
                passwordError = if (password.length >= 6) null else "Mínimo 6 caracteres"
            )
        }
        validForm()
    }

    fun onRepeatPasswordChange(repeatPassword: String, password: String){
        _state.update {
            it.copy(
                repeatePassword = repeatPassword,
                repeatePasswordError = if (repeatPassword == password) null else "Deben de coincidir las contraseñas"
            )
        }
    }

    private fun validForm() {
        val s = _state.value
        isFormValid.value = s.email.isNotBlank() &&
                s.username.isNotBlank() &&
                s.password.isNotBlank() &&
                s.emailError == null &&
                s.passwordError == null &&
                s.repeatePasswordError == null
        _state.value=state.value.copy(isValid = isFormValid.value)

    }

    fun register(){
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true, errorMessage = null) }
            try {
                _state.value = state.value.copy(isLoading = true)
                val registerCommand =
                    RegisterCommand(
                        email = _state.value.email,
                        password = _state.value.password,
                        username = _state.value.email,
                    )
                val result = RegisterUseCase(registerCommand)
            }catch(e: Exception)  {
                _state.update {
                    it.copy(
                        isLoading = false,
                        errorMessage = e.message ?: "Registrando..."
                    )
                }
            }
            finally {
                _state.value = _state.value.copy(isLoading = false)
            }
        }
    }



}