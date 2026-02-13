package ies.sequeros.dam.pmdm.gestionperifl.ui.components.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import ies.sequeros.dam.pmdm.gestionperifl.ui.components.main.MainComponent
import ies.sequeros.dam.pmdm.gestionperifl.ui.components.screens.viewmodels.UserProfileViewModel
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun MainScreen(
    onLogout: () -> Unit,
    viewModel: UserProfileViewModel = koinViewModel()
) {
    // Recogemos el estado reactivo del perfil
    val userProfile by viewModel.state.collectAsState()

    if (userProfile == null) {
        // Rueda de carga
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator()
        }
    } else {
        // Pasamos los datos al MainComponent
        MainComponent(
            userProfile = userProfile,
            onLogout = onLogout
        )
    }
}