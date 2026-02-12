package ies.sequeros.dam.pmdm.gestionperifl.application.commands

import kotlinx.serialization.Serializable

@Serializable
data class LoginCommand(
    val email: String,
    val password: String
)