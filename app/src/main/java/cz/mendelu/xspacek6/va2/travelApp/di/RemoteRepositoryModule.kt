package cz.mendelu.xspacek6.va2.travelApp.di

import cz.mendelu.xspacek6.va2.travelApp.communication.TravelAPI
import cz.mendelu.xspacek6.va2.travelApp.communication.TravelRemoteRepositoryImpl
import org.koin.dsl.module

val remoteRepositoryModule = module {
    single { provideTravelRemoteRepository(get()) }
}

fun provideTravelRemoteRepository(travelAPI: TravelAPI): TravelRemoteRepositoryImpl
        = TravelRemoteRepositoryImpl(travelAPI)