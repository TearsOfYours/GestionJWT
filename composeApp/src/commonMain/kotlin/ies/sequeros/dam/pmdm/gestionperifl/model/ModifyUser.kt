package ies.sequeros.dam.pmdm.gestionperifl.model

import kotlinx.serialization.Serializable

@Serializable
data class ModifyUser(
    val name: String,
    val status: String
)
