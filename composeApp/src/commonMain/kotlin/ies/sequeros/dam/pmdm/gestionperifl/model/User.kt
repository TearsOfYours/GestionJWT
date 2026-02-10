package ies.sequeros.dam.pmdm.gestionperifl.model

import kotlinx.serialization.Serializable
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

@Serializable()
data class User @OptIn(ExperimentalUuidApi::class) constructor(
    val username: String,
    val email: String,
    val id: Uuid)