package ies.sequeros.dam.pmdm.gestionperifl.ui.register

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import ies.sequeros.dam.pmdm.gestionperifl.ui.components.register.RegisterForm
import ies.sequeros.dam.pmdm.gestionperifl.ui.components.register.RegisterFormViewModel
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun RegisterScreen(
    onRegister: () -> Unit,
    onCancel: () -> Unit,
) {

    val viewModel = koinViewModel<RegisterFormViewModel>()
    val state by viewModel.state.collectAsState()

    LaunchedEffect(state.isRegisterSuccess) {
        if (state.isRegisterSuccess) {
            onRegister()
        }
    }

    RegisterForm(
        state = state,
        onUsernameChange = viewModel::onUsernameChange,
        onPasswordChange = viewModel::onPasswordChange,
        onRepeatedPasswordChange = viewModel::onRepeatPasswordChange,
        onEmailChange = viewModel::onEmailChange,
        onRegister = {  },
        onCancel = { onCancel() }
    )

}