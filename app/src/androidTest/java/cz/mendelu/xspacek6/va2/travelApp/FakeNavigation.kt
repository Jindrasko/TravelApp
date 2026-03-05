package cz.mendelu.xspacek6.va2.travelApp

import androidx.navigation.NavController
import cz.mendelu.xspacek6.va2.travelApp.TravelAppApplication.Companion.appContext
import cz.mendelu.xspacek6.va2.travelApp.navigation.INavigationRouter

class FakeNavigation: INavigationRouter {
    override fun getNavController(): NavController {
        return NavController(appContext)
    }

    override fun returnBack() {

    }

    override fun navigateToListOfPlaces() {

    }

    override fun navigateToPlaceDetail(id: String) {

    }

    override fun navigateToFilterScreen() {

    }

    override fun navigateToTrackPlaceScreen(id: String) {

    }
}