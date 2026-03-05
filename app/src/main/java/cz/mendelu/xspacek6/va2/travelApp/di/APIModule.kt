package cz.mendelu.xspacek6.va2.travelApp.di

import cz.mendelu.xspacek6.va2.travelApp.communication.TravelAPI
import org.koin.dsl.module
import retrofit2.Retrofit

val apiModule = module {
    single { provideTravelApi(get()) }
}

fun provideTravelApi(retrofit: Retrofit): TravelAPI
        = retrofit.create(TravelAPI::class.java)