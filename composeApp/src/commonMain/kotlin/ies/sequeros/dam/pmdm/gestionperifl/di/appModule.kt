package ies.sequeros.dam.pmdm.gestionperifl.di

import ies.sequeros.dam.pmdm.gestionperifl.application.usecases.LoginUseCase
import ies.sequeros.dam.pmdm.gestionperifl.application.usecases.RegisterUseCase
import ies.sequeros.dam.pmdm.gestionperifl.infrastructure.RestUserRepository
import ies.sequeros.dam.pmdm.gestionperifl.infrastructure.TokenStorage
import ies.sequeros.dam.pmdm.gestionperifl.infrastructure.ktor.createHttpClient
import ies.sequeros.dam.pmdm.gestionperifl.model.IUserRepository
import ies.sequeros.dam.pmdm.gestionperifl.ui.appsettings.AppSettings
import ies.sequeros.dam.pmdm.gestionperifl.ui.appsettings.AppViewModel
import ies.sequeros.dam.pmdm.gestionperifl.ui.components.register.RegisterFormViewModel
import ies.sequeros.dam.pmdm.gestionperifl.ui.components.login.LoginFormViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module


val appModulo = module {

    /**
     * infraestructura
     */
    single {
        createHttpClient( get(),
            "http://localhost:8080/api/public/refresh"
        )
    }
    //almacenamiento del token
    single { TokenStorage(get()) }


    //repositorios
    single<IUserRepository> {
        RestUserRepository(
            url = "http://localhost:8080/api/public",
            cliente = get(),
            tokenStorage = get()
        )
    }

    /**
    capa de aplicación
    el sesion manager,
    el origen de los datos, se encarga de transforar el tokenstorage para trabajar con user
    casos de uso
     **/

    /**
    capa de presentación
     **/
    single { AppSettings() }
    viewModel { AppViewModel(get(), get(), get()) }
    viewModel { LoginFormViewModel(get()) }

    viewModel { RegisterFormViewModel(get()) }
    factory { RegisterUseCase(get()) }
    factory { LoginUseCase(get()) }

}