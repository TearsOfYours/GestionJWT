package ies.sequeros.dam.pmdm.gestionperifl.model

interface IUserRepository {
    suspend fun loginUser(user: LoginUser): Map<String, String>
    suspend fun registerUser(user: RegisterUser)
    suspend fun logoutUser()
}