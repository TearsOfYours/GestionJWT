package ies.sequeros.dam.pmdm.gestionperifl.ui.components.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import ies.sequeros.dam.pmdm.gestionperifl.ui.components.screens.viewmodels.UserProfileViewModel
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun UserProfileScreen(
    viewModel: UserProfileViewModel = koinViewModel()
) {

    val user by viewModel.state.collectAsState()

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {

        if (user == null) {
            CircularProgressIndicator()
        } else {

            Card(
                modifier = Modifier.width(420.dp),
                elevation = CardDefaults.cardElevation(8.dp)
            ) {

                Column(
                    modifier = Modifier
                        .padding(24.dp)
                        .fillMaxWidth(),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {

                    Text(
                        text = "Perfil de Usuario",
                        style = MaterialTheme.typography.titleLarge
                    )

                    ReadOnlyField("ID", user!!.id)
                    ReadOnlyField("Nombre", user!!.name)
                    ReadOnlyField("Email", user!!.email)
                    ReadOnlyField("Estado", user!!.status)
                }
            }
        }
    }
}

@Composable
fun ReadOnlyField(
    label: String,
    value: String?
) {
    OutlinedTextField(
        value = value!!,
        onValueChange = {},
        label = { Text(label) },
        readOnly = true,
        enabled = false,
        modifier = Modifier.fillMaxWidth()
    )
}