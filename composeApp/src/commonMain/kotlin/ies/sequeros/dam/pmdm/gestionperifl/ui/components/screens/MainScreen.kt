package ies.sequeros.dam.pmdm.gestionperifl.ui.components.screens

import androidx.compose.runtime.Composable
import ies.sequeros.dam.pmdm.gestionperifl.ui.components.main.MainComponent

@Composable
fun MainScreen(
    onLogout: () -> Unit,
) {
    MainComponent(
        onLogout = {
            onLogout()
        },
    )
}