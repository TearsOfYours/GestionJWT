package ies.sequeros.dam.pmdm.gestionperifl.infrastructure

import ies.sequeros.dam.pmdm.gestionperifl.model.IUserRepository
import ies.sequeros.dam.pmdm.gestionperifl.model.User
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.client.utils.EmptyContent.contentType
import io.ktor.http.ContentType
import io.ktor.http.HttpStatusCode
import io.ktor.http.contentType
import kotlin.uuid.ExperimentalUuidApi

class RestUserRepository(
    private val url: String,
    private val cliente: HttpClient
): IUserRepository {

    override suspend fun loginUser(){
        TODO("Not yet implemented")
    }

    @OptIn(ExperimentalUuidApi::class)
    override  suspend fun registerUser(user: User): User {
        return cliente.post("$url/register") {
            contentType(ContentType.Application.Json)
            setBody(user)
        }.body()
    }

    override suspend fun logoutUser() {
        TODO("Not yet implemented")
    }


}