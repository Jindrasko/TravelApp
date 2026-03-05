package cz.mendelu.xspacek6.va2.travelApp.ui.screens

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import cz.mendelu.xspacek6.va2.travelApp.architecture.BaseViewModel
import cz.mendelu.xspacek6.va2.travelApp.database.IPlaceLocalRepository
import cz.mendelu.xspacek6.va2.travelApp.models.placeDetail.PlaceDetailResponse
import cz.mendelu.xspacek6.va2.travelApp.models.places.PlacesResponse
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class SavedPlacesViewModel(private val databaseRepository: IPlaceLocalRepository) : BaseViewModel() {

    val savedPlacesUiState: MutableState<SavedPlacesUiState<List<PlaceDetailResponse>>> = mutableStateOf(SavedPlacesUiState.Start())

    lateinit var places: List<PlaceDetailResponse>

    fun loadSavedPlaces(){
        launch {
            databaseRepository.getAll().collect {
                savedPlacesUiState.value = SavedPlacesUiState.Success(it)
                places = it
            }
        }
    }

}