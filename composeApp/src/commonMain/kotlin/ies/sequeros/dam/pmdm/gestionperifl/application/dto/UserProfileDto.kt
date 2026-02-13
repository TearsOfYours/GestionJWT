package ies.sequeros.dam.pmdm.gestionperifl.application.dto

import kotlinx.serialization.Serializable

@Serializable
data class UserProfileDto(
    val id: String,
    val name: String? = null,
    val email: String,
    val image: String? = null,
    val status: String,
)