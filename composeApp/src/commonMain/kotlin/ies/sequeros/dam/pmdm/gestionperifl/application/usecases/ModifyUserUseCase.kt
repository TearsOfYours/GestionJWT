package ies.sequeros.dam.pmdm.gestionperifl.application.usecases

import ies.sequeros.dam.pmdm.gestionperifl.application.commands.ModifyUserCommand
import ies.sequeros.dam.pmdm.gestionperifl.model.IUserRepository

class ModifyUserUseCase(private val repository: IUserRepository) {
    suspend fun modify(command: ModifyUserCommand): Result<Unit> {
        if (command.name.isBlank()) {
            return Result.failure(IllegalArgumentException("El nombre es obligatorios"))
        }
        repository.modifyUser(
            command.name,
            command.status
        )
        return Result.success(Unit)
    }
}