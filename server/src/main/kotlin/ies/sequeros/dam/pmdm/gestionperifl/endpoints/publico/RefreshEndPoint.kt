package ies.sequeros.dam.pmdm.gestionperifl.endpoints.publico

import ies.sequeros.dam.pmdm.gestionperifl.application.user.refresh.RefreshTokenUseCase
import ies.sequeros.dam.pmdm.gestionperifl.application.user.refresh.RefreshTokenUserCommand
import io.ktor.http.HttpStatusCode
import io.ktor.server.request.receive
import io.ktor.server.response.respond
import io.ktor.server.routing.Route
import io.ktor.server.routing.post
import io.ktor.server.routing.route
import org.koin.ktor.ext.inject
import kotlin.getValue

fun Route.refreshEndPoint() {
    //se inyecta fuera para mejorar el rendimiento
    val refreshUserUseCase by inject<RefreshTokenUseCase>()
    route("refresh") {
        //se valida el comando que llega con json schema, plugin creado pa ktor
        /*install(ValidateSchema) {
            schemaPath = "json_schemas/refresh-token-command.schema.json"
        }*/
        post() {
            val command = call.receive<RefreshTokenUserCommand>()
            val item = refreshUserUseCase(command)
            if (item == null) {
                call.respond(HttpStatusCode.Unauthorized, mapOf("error" to "Refresh token invalid or expired"))
            } else {
                call.respond(HttpStatusCode.OK, item)
            }
            //en caso de no haber saltado ninguna excepción, se  devuelve el
            //objeto de refresco

        }
    }
}