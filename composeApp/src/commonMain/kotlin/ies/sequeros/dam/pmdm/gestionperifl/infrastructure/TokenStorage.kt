package ies.sequeros.dam.pmdm.gestionperifl.infrastructure

import com.russhwolf.settings.Settings

class TokenStorage(private val settings: Settings) {
    companion object {
        private const val KEY_ACCESS_TOKEN = "access_token"
        private const val KEY_REFRESH_TOKEN = "refresh_token"
        private const val KEY_EMAIL = "email"
    }

    fun saveTokens(accessToken: String, refreshToken: String) {
        settings.putString(KEY_ACCESS_TOKEN, accessToken)
        settings.putString(KEY_REFRESH_TOKEN, refreshToken)
    }

    fun getAccessToken(): String? = settings.getStringOrNull(KEY_ACCESS_TOKEN)
    fun getRefreshToken(): String? = settings.getStringOrNull(KEY_REFRESH_TOKEN)

    fun clear() {
        settings.remove(KEY_ACCESS_TOKEN)
        settings.remove(KEY_REFRESH_TOKEN)
        settings.remove(KEY_EMAIL)
    }
    fun saveEmail(email: String) {
        settings.putString(KEY_EMAIL, email)
    }

    fun getEmail(): String? = settings.getStringOrNull(KEY_EMAIL)
}