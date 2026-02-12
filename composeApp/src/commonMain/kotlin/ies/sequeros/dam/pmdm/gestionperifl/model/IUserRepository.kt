package ies.sequeros.dam.pmdm.gestionperifl.model

interface IUserRepository {
    suspend fun loginUser(user: LoginUser): Map<String, String>
    suspend fun registerUser(user: RegisterUser)
    suspend fun logoutUser()
    suspend fun changePassword(oldPassword: String, newPassword: String)
    suspend fun modifyUser(name: String, status: String)
    suspend fun deleteUser(password: String)
}