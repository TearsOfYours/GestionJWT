package ies.sequeros.dam.pmdm.gestionperifl.ui.appsettings

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import ies.sequeros.dam.pmdm.gestionperifl.AppRoute
import ies.sequeros.dam.pmdm.gestionperifl.ui.components.main.MainComponent
import ies.sequeros.dam.pmdm.gestionperifl.ui.components.screens.LoginScreen

@Composable
fun AppNavigation() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = AppRoute.login
    ) {
        composable(AppRoute.login) {
            LoginScreen(
                navController = navController,
                onLogin = {
                    navController.navigate(AppRoute.main) {
                        popUpTo(AppRoute.login) { inclusive = true }
                    }
                },
                onCancel = {}
            )
        }

        composable(AppRoute.main) {
            MainComponent()
        }
    }
}
