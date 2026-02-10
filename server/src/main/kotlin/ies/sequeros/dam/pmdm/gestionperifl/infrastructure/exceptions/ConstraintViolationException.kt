package ies.sequeros.dam.pmdm.gestionperifl.infrastructure.exceptions

class ConstraintViolationException(val resourceName: String?, val reason: String?) : RuntimeException(
    String.format(
        "Conflicto en %s: %s",
        resourceName,
        reason
    )
)