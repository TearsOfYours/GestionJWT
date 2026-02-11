package ies.sequeros.dam.pmdm.gestionperifl.application.usecases

import ies.sequeros.dam.pmdm.gestionperifl.application.commands.LoginCommand
import ies.sequeros.dam.pmdm.gestionperifl.model.IUserRepository
import ies.sequeros.dam.pmdm.gestionperifl.model.LoginUser

class LoginUseCase (
    private val userRepository: IUserRepository
) {
    suspend fun login(command: LoginCommand): Result<Unit> {
        if (command.email.isBlank() || command.password.isBlank()) {
            return Result.failure(IllegalArgumentException("Están vacíos los campos"))
        }


        val loginUser = LoginUser(
            email = command.email,
            password = command.password
        )

        userRepository.loginUser(loginUser)
        return Result.success(Unit)
    }
}
