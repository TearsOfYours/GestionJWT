package ies.sequeros.dam.pmdm.gestionperifl.ui.components.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import ies.sequeros.dam.pmdm.gestionperifl.ui.components.deleteuser.DeleteUserViewModel
import org.koin.compose.viewmodel.koinViewModel
@Composable
fun DeleteUserScreen(viewModel: DeleteUserViewModel = koinViewModel(), onGoToLogin: () -> Unit
) {

    LaunchedEffect(viewModel.deleteSuccess) {
        if (viewModel.deleteSuccess) {
            onGoToLogin()
        }
    }

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Text("Eliminar cuenta")

            Spacer(modifier = Modifier.height(24.dp))

            OutlinedTextField(
                value = viewModel.password,
                onValueChange = { viewModel.password = it },
                label = { Text("Contraseña") },
                singleLine = true
            )

            Spacer(modifier = Modifier.height(24.dp))

            Button(
                onClick = { viewModel.deleteUser() }
            ) {
                Text("Borrar usuario")
            }

            if (viewModel.message.isNotEmpty()) {
                Spacer(Modifier.height(16.dp))
                Text(viewModel.message)
            }
        }
    }
}
