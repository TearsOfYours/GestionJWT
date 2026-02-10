package ies.sequeros.dam.pmdm.gestionperifl.model

interface IUserRepository {
    suspend fun loginUser()
    suspend fun registerUser(user: RegisterUser)
    suspend fun logoutUser()
}