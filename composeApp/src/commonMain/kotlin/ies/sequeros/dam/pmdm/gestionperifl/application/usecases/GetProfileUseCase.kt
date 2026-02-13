package ies.sequeros.dam.pmdm.gestionperifl.application.usecases

import ies.sequeros.dam.pmdm.gestionperifl.application.dto.UserProfileDto
import ies.sequeros.dam.pmdm.gestionperifl.model.IUserRepository

class GetProfileUseCase(
    private val repository: IUserRepository
) {
    suspend operator fun invoke(): UserProfileDto {
        return repository.getProfile()
    }
}