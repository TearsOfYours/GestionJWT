package ies.sequeros.dam.pmdm.gestionperifl.application.usecases

import ies.sequeros.dam.pmdm.gestionperifl.application.commands.DeleteUserCommand
import ies.sequeros.dam.pmdm.gestionperifl.model.IUserRepository

class DeleteUserUseCase(private val repository: IUserRepository) {
    suspend fun delete(command: DeleteUserCommand): Result<Unit> {
        if (command.password.isBlank()) {
            return Result.failure(IllegalArgumentException("La contraseña es obligatoria"))
        }
        repository.deleteUser(
            command.password)

        return Result.success(Unit)

    }
}
