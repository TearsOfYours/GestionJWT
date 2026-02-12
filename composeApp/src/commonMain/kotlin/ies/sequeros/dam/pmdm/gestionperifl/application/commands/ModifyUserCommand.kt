package ies.sequeros.dam.pmdm.gestionperifl.application.commands

import kotlinx.serialization.Serializable

@Serializable
data class ModifyUserCommand (
    val name: String,
    val status: String
)