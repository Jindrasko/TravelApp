package cz.mendelu.xspacek6.va2.travelApp.di

import cz.mendelu.xspacek6.va2.travelApp.ui.activities.AppIntroViewModel
import cz.mendelu.xspacek6.va2.travelApp.ui.activities.MainActivityViewModel
import cz.mendelu.xspacek6.va2.travelApp.ui.screens.*
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {

    viewModel { ListOfPlacesViewModel(get(), get()) }
    viewModel { PlaceDetailViewModel(get(), get()) }
    viewModel { SavedPlacesViewModel(get()) }
    viewModel { MainActivityViewModel(get()) }
    viewModel { AppIntroViewModel(get()) }
    viewModel { FilterScreenViewModel(get()) }
    viewModel { TrackPlaceViewModel(get(), get()) }

}