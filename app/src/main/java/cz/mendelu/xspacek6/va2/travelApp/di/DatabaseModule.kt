package cz.mendelu.xspacek6.va2.travelApp.di

import cz.mendelu.xspacek6.va2.travelApp.TravelAppApplication
import cz.mendelu.xspacek6.va2.travelApp.database.PlaceDatabase
import org.koin.dsl.module

val databaseModule = module {
    fun provideDatabase(): PlaceDatabase = PlaceDatabase.getDatabase(TravelAppApplication.appContext)
    single {
        provideDatabase()
    }
}