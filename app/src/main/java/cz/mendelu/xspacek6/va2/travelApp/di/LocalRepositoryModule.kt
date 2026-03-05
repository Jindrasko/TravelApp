package cz.mendelu.xspacek6.va2.travelApp.di

import cz.mendelu.xspacek6.va2.travelApp.database.IPlaceLocalRepository
import cz.mendelu.xspacek6.va2.travelApp.database.PlacesDao
import cz.mendelu.xspacek6.va2.travelApp.database.PlacesLocalRepositoryImpl
import org.koin.dsl.module

val localRepositoryModule = module {
    fun provideLocalTaskRepository(dao: PlacesDao): IPlaceLocalRepository {
        return PlacesLocalRepositoryImpl(dao)
    }
    single { provideLocalTaskRepository(get()) }
}