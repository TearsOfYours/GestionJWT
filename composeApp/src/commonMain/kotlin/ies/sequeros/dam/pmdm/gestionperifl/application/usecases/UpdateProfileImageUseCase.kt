package ies.sequeros.dam.pmdm.gestionperifl.application.usecases

import ies.sequeros.dam.pmdm.gestionperifl.application.dto.UserProfileDto
import ies.sequeros.dam.pmdm.gestionperifl.model.IUserRepository

class UpdateProfileImageUseCase(
    private val repository: IUserRepository
) {
    suspend operator fun invoke(fileBytes: ByteArray?, filename: String): UserProfileDto {
        if (fileBytes == null) throw IllegalArgumentException("No hay imagen seleccionada")
        return repository.updateProfileImage(fileBytes, filename)
    }
}