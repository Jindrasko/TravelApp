package cz.mendelu.xspacek6.va2.travelApp.ui.screens

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import cz.mendelu.xspacek6.va2.travelApp.R
import cz.mendelu.xspacek6.va2.travelApp.architecture.BaseViewModel
import cz.mendelu.xspacek6.va2.travelApp.architecture.CommunicationResult
import cz.mendelu.xspacek6.va2.travelApp.communication.TravelRemoteRepositoryImpl
import cz.mendelu.xspacek6.va2.travelApp.constants.Constants
import cz.mendelu.xspacek6.va2.travelApp.database.IPlaceLocalRepository
import cz.mendelu.xspacek6.va2.travelApp.models.placeDetail.PlaceDetailResponse
import kotlinx.coroutines.*

class PlaceDetailViewModel(private val travelRepository: TravelRemoteRepositoryImpl, private val databaseRepository: IPlaceLocalRepository) : BaseViewModel() {

    val placeDetailUiState: MutableState<PlaceDetailUiState<PlaceDetailResponse>>
            = mutableStateOf(PlaceDetailUiState.Start())

    var placeId: String? = null

    var isDeleteVisible = mutableStateOf(false)

    var checkedState = mutableStateOf(false)

    lateinit var place: PlaceDetailResponse

    fun loadPlaceFromDatabase(){
        launch {
            databaseRepository.findById(placeId!!)?.let {
                place = it
                placeDetailUiState.value =
                    PlaceDetailUiState.Success(it)
                if (place.favorte) {
                    checkedState.value = true
                }
                if(place.done) {
                    isDeleteVisible.value = true
                }
            }
            if (!::place.isInitialized) {
                loadPlaceFromAPI()
            }

        }
    }

    fun loadPlaceFromAPI(){
        if(placeId != null) {
            launch {
                if (!::place.isInitialized) {

                    val result = withContext(Dispatchers.IO) {
                        travelRepository.place(placeId!!, api = Constants.API_KEY)
                    }

                    when (result) {
                        is CommunicationResult.Error -> placeDetailUiState.value =
                            PlaceDetailUiState.Error(R.string.detail_failed)
                        is CommunicationResult.Exception -> placeDetailUiState.value =
                            PlaceDetailUiState.Error(R.string.no_interned_connection)
                        is CommunicationResult.Success -> {
                            placeDetailUiState.value =
                                PlaceDetailUiState.Success(result.data)
                            place = PlaceDetailUiState.Success(result.data).data

                            println("PLACE INITIALIZED")
                        }
                    }
                }
            }
        } else {
            placeDetailUiState.value = PlaceDetailUiState.Error(R.string.detail_failed)
        }
    }

    fun starAction(){
        if (checkedState.value) {
            place.favorte = true
            savePlace()
            println("Star set true")
        } else {
            if(place.done){
                place.favorte = false
                savePlace()
                println("star set false")
            } else {
                deletePlace()
                println("place deleted")
            }
        }
    }



    fun savePlace(){
        launch {
            databaseRepository.insertPlace(place)
        }
    }

    fun deletePlace(){
        launch {
            databaseRepository.deletePlace(place)
        }
    }


}