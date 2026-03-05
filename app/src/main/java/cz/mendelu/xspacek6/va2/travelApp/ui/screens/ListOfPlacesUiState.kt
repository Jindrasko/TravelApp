package cz.mendelu.xspacek6.va2.travelApp.ui.screens

sealed class ListOfPlacesUiState<out T> {
    class Start() : ListOfPlacesUiState<Nothing>()
    class Success<T>(var data: T) : ListOfPlacesUiState<T>()
    class Error(var error: Int) : ListOfPlacesUiState<Nothing>()
}