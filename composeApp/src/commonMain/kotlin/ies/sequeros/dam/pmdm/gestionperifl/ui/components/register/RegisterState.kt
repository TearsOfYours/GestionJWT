package ies.sequeros.dam.pmdm.gestionperifl.ui.components.register

data class RegisterState(
    val username: String = "",
    val email: String = "paco@paco.es",
    val password: String = "T3st@Pass",
    val repeatePassword: String = "T3st@Pass",

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
