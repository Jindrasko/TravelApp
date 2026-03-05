package cz.mendelu.xspacek6.va2.travelApp.ui.screens

import android.annotation.SuppressLint
import android.location.Location
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.platform.LocalContext
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import cz.mendelu.xspacek6.va2.travelApp.R
import cz.mendelu.xspacek6.va2.travelApp.TravelAppApplication.Companion.appContext
import cz.mendelu.xspacek6.va2.travelApp.architecture.BaseViewModel
import cz.mendelu.xspacek6.va2.travelApp.architecture.CommunicationResult
import cz.mendelu.xspacek6.va2.travelApp.communication.TravelRemoteRepositoryImpl
import cz.mendelu.xspacek6.va2.travelApp.constants.Constants
import cz.mendelu.xspacek6.va2.travelApp.datastore.IDataStoreRepository
import cz.mendelu.xspacek6.va2.travelApp.map.DefaultLocationClient
import cz.mendelu.xspacek6.va2.travelApp.models.places.PlacesResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.last
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class ListOfPlacesViewModel(private var travelRepository: TravelRemoteRepositoryImpl, private val dataStoreRepository: IDataStoreRepository) : BaseViewModel() {

    val dataStore = dataStoreRepository
    val placesUiState: MutableState<ListOfPlacesUiState<PlacesResponse>>
            = mutableStateOf(ListOfPlacesUiState.Start())

    var longitude: Double = 16.606836
    var latitude: Double = 49.195061


    //val locationClient = DefaultLocationClient(appContext, LocationServices.getFusedLocationProviderClient(appContext))
    //val currentLocation: Flow<Location> = locationClient.getLocationUpdates(10000)

    //val location: Location = locationClient.getLastLocation()

    /*
    suspend fun getLatLon() {
        var location = currentLocation.last()
        latitude = location.latitude
        println("lat: " + latitude)
        longitude = location.longitude
        println("lon: " + longitude)
    }
    */

    fun loadPlaces(){
        launch {
            println("In loadPlaces()")

            //getLatLon()

            val result = withContext(Dispatchers.IO) {
                travelRepository.places(
                    radius = dataStoreRepository.getRadius(),
                    latitude = latitude,
                    longitude = longitude,
                    categories = dataStoreRepository.getCategories(),
                    limit = dataStoreRepository.getLimit(),
                    rate = "3h",
                    api = Constants.API_KEY
                )
            }

            when(result) {
                is CommunicationResult.Error -> placesUiState.value =
                    ListOfPlacesUiState.Error(R.string.list_failed)
                is CommunicationResult.Exception -> placesUiState.value =
                    ListOfPlacesUiState.Error(R.string.no_interned_connection)
                is CommunicationResult.Success -> placesUiState.value =
                    ListOfPlacesUiState.Success(result.data)
            }

        }
    }

    /*
        private fun getCategoriesString(): String{
            var final = ""

            categories.forEach { category ->
                final += "${category},"
            }
            return final.dropLast(1)
        }
    */

}