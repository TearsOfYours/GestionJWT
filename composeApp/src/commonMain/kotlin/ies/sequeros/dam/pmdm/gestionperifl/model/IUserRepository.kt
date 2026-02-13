package ies.sequeros.dam.pmdm.gestionperifl.model

import ies.sequeros.dam.pmdm.gestionperifl.application.dto.UserProfileDto

interface IUserRepository {
    suspend fun loginUser(user: LoginUser): Map<String, String>
    suspend fun registerUser(user: RegisterUser)
    suspend fun logoutUser()
    suspend fun getProfile(): UserProfileDto
    suspend fun updateProfileImage(fileBytes: ByteArray, filename: String): UserProfileDto

    suspend fun refreshTokens(): String
}