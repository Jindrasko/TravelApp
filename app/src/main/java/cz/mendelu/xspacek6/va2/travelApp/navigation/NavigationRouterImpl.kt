package cz.mendelu.xspacek6.va2.travelApp.navigation

import android.os.Bundle
import androidx.navigation.NavController
import cz.mendelu.xspacek6.va2.travelApp.models.placeDetail.PlaceDetailResponse

class NavigationRouterImpl(private val navController: NavController) : INavigationRouter {

    override fun getNavController(): NavController = navController

    override fun returnBack() {
        navController.popBackStack()
    }

    override fun navigateToListOfPlaces() {
        navController.navigate(Destination.ListOfPlacesScreen.route)
    }

    override fun navigateToPlaceDetail(id: String) {
        navController.navigate(Destination.PlaceDetailScreen.route + "/" + id)
    }

    /*
    override fun navigateToMapOfPlaces() {
        navController.navigate(Destination.MapOfPlacesScreen.route)
    }*/

    override fun navigateToFilterScreen() {
        navController.navigate(Destination.FilterScreen.route)
    }

    override fun navigateToTrackPlaceScreen(id: String) {
        navController.navigate(Destination.TrackPlaceScreen.route + "/" + id)
    }


}