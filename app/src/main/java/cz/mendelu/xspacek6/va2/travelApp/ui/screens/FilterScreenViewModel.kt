package cz.mendelu.xspacek6.va2.travelApp.ui.screens

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.datastore.dataStore
import cz.mendelu.xspacek6.va2.travelApp.architecture.BaseViewModel
import cz.mendelu.xspacek6.va2.travelApp.datastore.DataStoreRepositoryImpl
import cz.mendelu.xspacek6.va2.travelApp.datastore.IDataStoreRepository
import kotlinx.coroutines.launch

class FilterScreenViewModel(
    private val dataStoreRepository: IDataStoreRepository
) : BaseViewModel() {

    val filterUiState: MutableState<FilterScreenUiStates<Boolean>> = mutableStateOf(FilterScreenUiStates.Start())

    var radius: Int = 100
    var limit: Int = 20

    fun loadPreferences(){
        launch {
            radius = dataStoreRepository.getRadius()
            limit = dataStoreRepository.getLimit()
            filterUiState.value = FilterScreenUiStates.Success(data = true)
        }
    }

    fun savePreferences(
        radius: Int,
        limit: Int
    ){
        launch {
            dataStoreRepository.setRadius(radius)
            dataStoreRepository.setLimit(limit)
        }
    }

}