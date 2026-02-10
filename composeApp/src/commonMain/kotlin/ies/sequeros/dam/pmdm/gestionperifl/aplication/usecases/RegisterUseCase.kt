package ies.sequeros.dam.pmdm.gestionperifl.aplication.usecases

import ies.sequeros.dam.pmdm.gestionperifl.aplication.commands.RegisterCommand

import ies.sequeros.dam.pmdm.gestionperifl.model.IUserRepository
import ies.sequeros.dam.pmdm.gestionperifl.model.RegisterUser
import ies.sequeros.dam.pmdm.gestionperifl.model.User
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

class RegisterUseCase(private val userRepository: IUserRepository) {


    // Si ocurre un error devuelve el texto de abajo, si no, el UI propio se encargará de mostrar un mensaje
    suspend fun execute(command: RegisterCommand): Result<Unit> {
        if (command.username.isBlank() || command.email.isBlank() || command.password.isBlank()) {
            return Result.failure(IllegalArgumentException("Todos los campos son obligatorios"))
        }


        //Crear el usuario desde la información del registro y después el servidor añadirá todos los demás elementos UUID, Hashpassword, etc

        val newUser = RegisterUser(
            username = command.username,
            email = command.email,
            password = command.password
        )

        userRepository.registerUser(newUser)

        return Result.success(Unit)
    }
}