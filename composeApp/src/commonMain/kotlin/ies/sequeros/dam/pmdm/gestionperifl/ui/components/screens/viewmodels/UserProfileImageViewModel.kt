package ies.sequeros.dam.pmdm.gestionperifl.ui.components.screens.viewmodels

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ies.sequeros.dam.pmdm.gestionperifl.application.dto.UserProfileDto
import ies.sequeros.dam.pmdm.gestionperifl.application.usecases.UpdateProfileImageUseCase
import io.github.vinceglb.filekit.PlatformFile
import io.github.vinceglb.filekit.name
import io.github.vinceglb.filekit.readBytes
import kotlinx.coroutines.launch

class UserProfileImageViewModel(
    private val updateImageUseCase: UpdateProfileImageUseCase
) : ViewModel() {

    var selectedFile by mutableStateOf<PlatformFile?>(null)
        private set

    var loading by mutableStateOf(false)
        private set

    var errorMessage by mutableStateOf<String?>(null)
        private set

    var profile by mutableStateOf<UserProfileDto?>(null)
        private set

    fun onFileSelected(file: PlatformFile?) {
        selectedFile = file
    }

    fun clearSelection() {
        selectedFile = null
    }

    fun confirmImage() {
        val file = selectedFile ?: return
        loading = true
        errorMessage = null

        viewModelScope.launch {
            try {
                val updatedProfile = updateImageUseCase(
                    fileBytes = file.readBytes(),
                    filename = file.name
                )
                profile = updatedProfile // aquí actualizas directamente
                selectedFile = null
            } catch (e: Exception) {
                errorMessage = e.message
            } finally {
                loading = false
            }
        }
    }
}