package cz.mendelu.xspacek6.va2.travelApp.ui.screens

sealed class FilterScreenUiStates<out T> {
    class Start() : FilterScreenUiStates<Nothing>()
    class Success<T>(var data: T) : FilterScreenUiStates<T>()
}