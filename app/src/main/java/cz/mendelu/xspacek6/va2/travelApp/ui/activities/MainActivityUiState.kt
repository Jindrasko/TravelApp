package cz.mendelu.xspacek6.va2.travelApp.ui.activities

sealed class MainActivityUiState {
    object Default : MainActivityUiState()
    object RunForAFirstTime : MainActivityUiState()
    object ContinueToApp : MainActivityUiState()
}