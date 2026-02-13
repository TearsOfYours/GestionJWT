package ies.sequeros.dam.pmdm.gestionperifl.ui.components.main

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ExitToApp
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.material3.adaptive.currentWindowAdaptiveInfo
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.window.core.layout.WindowWidthSizeClass
import coil3.compose.AsyncImage
import ies.sequeros.dam.pmdm.gestionperifl.application.dto.UserProfileDto
import ies.sequeros.dam.pmdm.gestionperifl.ui.components.ImagePickerPreviewComponent
import ies.sequeros.dam.pmdm.gestionperifl.ui.components.screens.UserProfileScreen
import ies.sequeros.dam.pmdm.gestionperifl.ui.components.screens.viewmodels.UserProfileImageViewModel
import ies.sequeros.dam.pmdm.gestionperifl.ui.components.screens.viewmodels.UserProfileViewModel
import org.koin.compose.viewmodel.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainComponent(
    onLogout: () -> Unit,
    userProfile: UserProfileDto? // Recibimos el perfil del usuario
) {

    val navController = rememberNavController()
    val adaptiveInfo = currentWindowAdaptiveInfo()
    var showLogoutDialog by remember { mutableStateOf(false) }

    val items = listOf(
        Pair(Icons.Default.Lock, MainRoutes.CambiarPassword),
        Pair(Icons.Default.Photo, MainRoutes.CambiarImagen),
        Pair(Icons.Default.Edit, MainRoutes.ModificarUsuario),
        Pair(Icons.Default.Delete, MainRoutes.BorrarUsuario),
    )

    val navegador: @Composable () -> Unit = {
        NavHost(
            navController = navController,
            startDestination = MainRoutes.Perfil
        ) {
            composable(MainRoutes.Perfil) { UserProfileScreen() }
            composable(MainRoutes.CambiarPassword) { Text("Cambiar Contraseña") }
            composable(MainRoutes.CambiarImagen) {
                val viewModel: UserProfileImageViewModel = koinViewModel()
                ImagePickerPreviewComponent(
                    imageUrl = viewModel.profile?.image,
                    selectedFile = viewModel.selectedFile,
                    onFileSelected = { viewModel.onFileSelected(it) },
                    onConfirm = { viewModel.confirmImage() }
                )
                viewModel.errorMessage?.let { error ->
                    Text(error)
                }
            }
            composable(MainRoutes.ModificarUsuario) { Text("Modificar Usuario") }
            composable(MainRoutes.BorrarUsuario) { Text("Borrar Usuario") }
        }
    }

    // ---------------- MÓVIL ----------------
    if (adaptiveInfo.windowSizeClass.windowWidthSizeClass == WindowWidthSizeClass.COMPACT) {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = {},
                    actions = {
                        IconButton(onClick = { navController.navigate(MainRoutes.Perfil) }) {
                            if (userProfile?.image.isNullOrEmpty()) {
                                Icon(Icons.Default.Person, contentDescription = "Perfil")
                            } else {
                                AsyncImage(
                                    model = userProfile?.image,
                                    contentDescription = "Perfil",
                                    modifier = Modifier
                                        .size(32.dp)
                                        .clip(CircleShape)
                                )
                            }
                        }
                    }
                )
            },
            bottomBar = {
                NavigationBar {
                    items.forEach { item ->
                        NavigationBarItem(
                            selected = false,
                            onClick = { navController.navigate(item.second) },
                            icon = { Icon(item.first, contentDescription = item.second) }
                        )
                    }
                    NavigationBarItem(
                        selected = false,
                        onClick = { showLogoutDialog = true },
                        icon = { Icon(Icons.AutoMirrored.Filled.ExitToApp, contentDescription = "Cerrar sesión", tint = MaterialTheme.colorScheme.error) }
                    )
                }
            }
        ) { innerPadding ->
            Box(Modifier.padding(innerPadding)) {
                navegador()
            }
        }

    } else {

        // ---------------- ESCRITORIO ----------------
        PermanentNavigationDrawer(
            drawerContent = {
                PermanentDrawerSheet(
                    Modifier.width(128.dp)
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxHeight()
                            .padding(vertical = 16.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {

                        // ---------------- AVATAR ----------------
                        IconButton(
                            onClick = { navController.navigate(MainRoutes.Perfil) },
                            modifier = Modifier.size(64.dp)
                        ) {
                            if (userProfile?.image.isNullOrEmpty()) {
                                Icon(
                                    Icons.Default.Person,
                                    contentDescription = "Perfil",
                                    modifier = Modifier.size(48.dp)
                                )
                            } else {
                                AsyncImage(
                                    model = userProfile.image,
                                    contentDescription = "Perfil",
                                    modifier = Modifier
                                        .size(48.dp)
                                        .clip(CircleShape)
                                )
                            }
                        }

                        Spacer(Modifier.height(16.dp))

                        items.forEach { item ->
                            NavigationDrawerItem(
                                icon = { Icon(item.first, contentDescription = item.second) },
                                label = {},
                                selected = false,
                                onClick = { navController.navigate(item.second) },
                                modifier = Modifier.padding(vertical = 4.dp)
                            )
                        }

                        Spacer(modifier = Modifier.weight(1f))

                        NavigationDrawerItem(
                            icon = { Icon(Icons.AutoMirrored.Filled.ExitToApp, contentDescription = "Cerrar sesión", tint = MaterialTheme.colorScheme.error) },
                            label = {},
                            selected = false,
                            onClick = { showLogoutDialog = true }
                        )
                    }
                }
            },
            content = {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    navegador()
                }
            }
        )
    }

    // ---------------- DIALOG CONFIRMACIÓN ----------------
    if (showLogoutDialog) {
        AlertDialog(
            onDismissRequest = { showLogoutDialog = false },
            title = { Text("Cerrar sesión") },
            text = { Text("¿Estás seguro de que quieres cerrar sesión?") },
            confirmButton = {
                TextButton(onClick = {
                    showLogoutDialog = false
                    onLogout()
                }) { Text("Sí") }
            },
            dismissButton = {
                TextButton(onClick = { showLogoutDialog = false }) { Text("Cancelar") }
            }
        )
    }
}