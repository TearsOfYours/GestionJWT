package ies.sequeros.dam.pmdm.gestionperifl.infrastructure

import ies.sequeros.dam.pmdm.gestionperifl.model.IUserRepository
import io.ktor.client.plugins.auth.providers.BearerTokens

class RestUserRepository(
    private val url: String,
    private val cliente: BearerTokens
): IUserRepository {

    override suspend fun loginUser(){
        TODO("Not yet implemented")
    }
    override suspend fun registerUser() {
        TODO("Not yet implemented")
    }

    override suspend fun logoutUser() {
        TODO("Not yet implemented")
    }


}