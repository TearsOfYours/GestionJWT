package ies.sequeros.dam.pmdm.gestionperifl.ui.components.register

data class RegisterState(
    val username: String = "",
    val email: String = "paco@paco.es",
    val password: String = "1234567%8Pp",
    val repeatePassword: String = "1234567%8Pp",

    // UI States
    val isLoading: Boolean = false,
    val isRegisterSuccess: Boolean = false,
    val isValid:Boolean = false,
    // Errores específicos de campo (validación local)

    val usernameError: String? = null,
    val emailError: String? = null,
    val passwordError: String? = null,
    val repeatePasswordError: String? = null,

    // Error global (ej: "Credenciales incorrectas" o "No hay internet")
    val errorMessage: String? = null

)
