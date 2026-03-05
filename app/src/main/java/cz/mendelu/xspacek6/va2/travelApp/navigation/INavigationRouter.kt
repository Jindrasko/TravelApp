package cz.mendelu.xspacek6.va2.travelApp.navigation

import android.os.Bundle
import androidx.navigation.NavController
import cz.mendelu.xspacek6.va2.travelApp.models.placeDetail.PlaceDetailResponse

interface INavigationRouter {
    fun getNavController(): NavController
    fun returnBack()
    fun navigateToListOfPlaces()
    fun navigateToPlaceDetail(id: String)
    //fun navigateToMapOfPlaces()
    fun navigateToFilterScreen()
    fun navigateToTrackPlaceScreen(id: String)

}