package ies.sequeros.dam.pmdm.gestionperifl.infrastructure.exceptions


class DatabaseOperationException(
    val resourceName: String?, //  "crear", "actualizar", "borrar"
    val operation: String?, cause: Throwable
) : RuntimeException(
    String.format(
        "Error en %s al %s: %s",
        resourceName,operation,
         cause.message
    ), cause
)