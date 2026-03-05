package cz.mendelu.xspacek6.va2.travelApp.ui.activities

import cz.mendelu.xspacek6.va2.travelApp.architecture.BaseViewModel
import cz.mendelu.xspacek6.va2.travelApp.datastore.IDataStoreRepository

class AppIntroViewModel(private val dataStoreRepository: IDataStoreRepository): BaseViewModel() {
    suspend fun setFirstRun(){
        dataStoreRepository.setFirstRun()
    }
}