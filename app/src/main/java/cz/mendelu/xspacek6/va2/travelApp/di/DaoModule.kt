package cz.mendelu.xspacek6.va2.travelApp.di

import cz.mendelu.xspacek6.va2.travelApp.database.PlaceDatabase
import cz.mendelu.xspacek6.va2.travelApp.database.PlacesDao
import org.koin.dsl.module

val daoModule = module {
    fun providePlacesDao(database: PlaceDatabase): PlacesDao = database.placesDao()
    single {
        providePlacesDao(get())
    }
}