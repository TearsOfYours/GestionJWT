package ies.sequeros.dam.pmdm.gestionperifl.ui.components.register

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import org.koin.compose.viewmodel.koinViewModel

@Composable

fun RegisterForm(
    state: RegisterState,
    onUsernameChange: (String) -> Unit,
    onPasswordChange: (String) -> Unit,
    onRepeatedPasswordChange: (String) -> Unit,
    onEmailChange: (String) -> Unit,
    onRegister: () -> Unit,
    onCancel: () -> Unit
) {

    val registerFormViewModel = koinViewModel<RegisterFormViewModel>()
    val state by registerFormViewModel.state.collectAsState()


    Column() {
        OutlinedTextField(
            value = state.username,
            onValueChange = { onUsernameChange(it) }
        )

    }
}