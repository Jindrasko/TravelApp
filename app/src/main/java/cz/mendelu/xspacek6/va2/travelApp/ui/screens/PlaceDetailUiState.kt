package cz.mendelu.xspacek6.va2.travelApp.ui.screens


sealed class PlaceDetailUiState<out T> {
    class Start() : PlaceDetailUiState<Nothing>()
    class Success<T>(var data: T) : PlaceDetailUiState<T>()
    class Error(var error: Int) : PlaceDetailUiState<Nothing>()
}