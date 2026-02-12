package ies.sequeros.dam.pmdm.gestionperifl.ui.components.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import ies.sequeros.dam.pmdm.gestionperifl.ui.components.modifyuser.ModifyUserViewModel
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun ModifyUserScreen(
    viewModel: ModifyUserViewModel = koinViewModel()
) {

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {

        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Text(
                text = "Modificar Perfil",
                style = MaterialTheme.typography.headlineMedium
            )

            Spacer(Modifier.height(24.dp))

            OutlinedTextField(
                value = viewModel.name,
                onValueChange = { viewModel.name = it },
                label = { Text("Nombre") },
                singleLine = true
            )

            Spacer(Modifier.height(16.dp))

            OutlinedTextField(
                value = viewModel.status,
                onValueChange = { viewModel.status = it },
                label = { Text("Estado") },
                singleLine = true
            )

            Spacer(Modifier.height(24.dp))

            Button(
                onClick = { viewModel.modifyUser() },
                enabled = !viewModel.isLoading
            ) {
                Text("Guardar cambios")
            }

            if (viewModel.isLoading) {
                Spacer(Modifier.height(16.dp))
                Text("Cargando...")
            }

            if (viewModel.message.isNotEmpty()) {
                Spacer(Modifier.height(16.dp))
                Text(viewModel.message)
            }
        }
    }
}

