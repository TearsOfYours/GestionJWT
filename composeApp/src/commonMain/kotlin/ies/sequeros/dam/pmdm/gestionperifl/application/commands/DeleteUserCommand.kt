package ies.sequeros.dam.pmdm.gestionperifl.application.commands

import kotlinx.serialization.Serializable

@Serializable
data class DeleteUserCommand (
    val password: String
)