package cz.mendelu.xspacek6.va2.travelApp.navigation

sealed class Destination(
    val route: String
){
    object ListOfPlacesScreen : Destination(route = "list_of_places")
    object PlaceDetailScreen: Destination(route = "place_detail")
    //object MapOfPlacesScreen : Destination(route = "map_of_places")
    object FilterScreen : Destination(route = "filter_screen")
    object TrackPlaceScreen : Destination(route = "track_screen")

}