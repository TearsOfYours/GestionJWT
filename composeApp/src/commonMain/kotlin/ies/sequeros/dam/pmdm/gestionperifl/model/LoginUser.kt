package ies.sequeros.dam.pmdm.gestionperifl.model

import kotlinx.serialization.Serializable


@Serializable
data class LoginUser(
    val email: String,
    val password: String
)