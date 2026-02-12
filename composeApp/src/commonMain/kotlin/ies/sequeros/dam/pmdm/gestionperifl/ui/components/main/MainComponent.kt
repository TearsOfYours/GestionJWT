package ies.sequeros.dam.pmdm.gestionperifl.ui.components.main

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Photo
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.PermanentDrawerSheet
import androidx.compose.material3.PermanentNavigationDrawer
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.adaptive.currentWindowAdaptiveInfo
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.window.core.layout.WindowWidthSizeClass
import ies.sequeros.dam.pmdm.gestionperifl.AppRoute
import ies.sequeros.dam.pmdm.gestionperifl.ui.components.screens.ChangePasswordScreen
import ies.sequeros.dam.pmdm.gestionperifl.ui.components.screens.DeleteUserScreen
import ies.sequeros.dam.pmdm.gestionperifl.ui.components.screens.LoginScreen
import ies.sequeros.dam.pmdm.gestionperifl.ui.components.screens.ModifyUserScreen

@Composable
fun MainComponent(onLogout: () -> Unit) {

    val navController = rememberNavController()
    val adaptiveInfo = currentWindowAdaptiveInfo()

    val items = listOf(
        Pair(Icons.Default.Person, MainRoutes.Perfil),
        Pair(Icons.Default.Lock, MainRoutes.CambiarPassword),
        Pair(Icons.Default.Photo, MainRoutes.CambiarImagen),
        Pair(Icons.Default.Edit, MainRoutes.ModificarUsuario),
        Pair(Icons.Default.Delete, MainRoutes.BorrarUsuario)
    )

    val navegador: @Composable () -> Unit = {
        NavHost(
            navController = navController,
            startDestination = MainRoutes.Perfil

        ) {
            composable(AppRoute.login) {
                LoginScreen(
                    navController = navController,
                    onLogin = {
                        navController.navigate(AppRoute.login) {
                            popUpTo(0)
                        }
                    },
                    onCancel = { }
                )
            }

            composable(MainRoutes.Perfil) { Text("Perfil Usuario") }
            composable(MainRoutes.CambiarPassword) {
                ChangePasswordScreen(onGoToLogin = {
                onLogout()
            })
            }
            composable(MainRoutes.CambiarImagen) { Text("Cambiar Imagen") }
            composable(MainRoutes.ModificarUsuario) { ModifyUserScreen() }
            composable(MainRoutes.BorrarUsuario) {
                DeleteUserScreen(onGoToLogin = {
                    onLogout()
                })
            }
        }
    }

    // Parte móvil
    if (adaptiveInfo.windowSizeClass.windowWidthSizeClass == WindowWidthSizeClass.COMPACT) {
        Scaffold(
            bottomBar = {
                NavigationBar {
                    items.forEach { item ->
                        NavigationBarItem(
                            selected = false,
                            onClick = { navController.navigate(item.second) },
                            icon = { Icon(item.first, contentDescription = item.second) }
                        )
                    }
                }
            }
        ) { innerPadding ->
            Box(Modifier.padding(innerPadding)) { navegador() }
        }
    } else {
        // Parte escritorio
        PermanentNavigationDrawer(
            drawerContent = {
                PermanentDrawerSheet(Modifier.width(128.dp)) {
                    Column(
                        modifier = Modifier
                            .fillMaxHeight()
                            .padding(vertical = 16.dp),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Spacer(Modifier.height(16.dp))
                        items.forEach { item ->
                            NavigationDrawerItem(
                                icon = {
                                    Box(
                                        modifier = Modifier.fillMaxWidth(),
                                        contentAlignment = Alignment.Center
                                    ) {
                                        Icon(
                                            item.first,
                                            contentDescription = item.second,
                                            tint = MaterialTheme.colorScheme.primary
                                        )
                                    }
                                },
                                label = {},
                                selected = false,
                                onClick = { navController.navigate(item.second) },
                                modifier = Modifier.padding(vertical = 4.dp)
                            )
                        }
                    }
                }
            },
            content = {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp)
                        .height(600.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) { navegador() }
            }
        )
    }
}
