package ies.sequeros.dam.pmdm.gestionperifl.application.usecases

import ies.sequeros.dam.pmdm.gestionperifl.application.commands.ChangePasswordCommand
import ies.sequeros.dam.pmdm.gestionperifl.model.IUserRepository

class ChangePasswordUseCase (private val repository: IUserRepository){
    suspend fun change(command: ChangePasswordCommand): Result<Unit> {
        if (command.oldPassword.isBlank() || command.newPassword.isBlank()) {
            return Result.failure(IllegalArgumentException("Todos los campos son obligatorios"))
    }
        repository.changePassword(
            command.oldPassword,
            command.newPassword
        )
        return Result.success(Unit)
    }
}
