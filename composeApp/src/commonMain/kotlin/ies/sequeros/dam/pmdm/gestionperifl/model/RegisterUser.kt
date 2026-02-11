package ies.sequeros.dam.pmdm.gestionperifl.model

import kotlinx.serialization.Serializable

@Serializable
data class RegisterUser(
    val username: String,
    val password: String,
    val email: String
)
