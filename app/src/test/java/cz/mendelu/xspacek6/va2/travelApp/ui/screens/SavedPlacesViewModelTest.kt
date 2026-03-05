package cz.mendelu.xspacek6.va2.travelApp.ui.screens

import cz.mendelu.xspacek6.va2.travelApp.database.data.repository.FakeDatabaseRepository
import cz.mendelu.xspacek6.va2.travelApp.models.placeDetail.PlaceDetailResponse
import cz.mendelu.xspacek6.va2.travelApp.models.placeDetail.Point
import cz.mendelu.xspacek6.va2.travelApp.models.placeDetail.Sources
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.*
import org.junit.Before

import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class SavedPlacesViewModelTest {

    private lateinit var savedPlacesVM: SavedPlacesViewModel
    private lateinit var fakeDatabaseRepository: FakeDatabaseRepository

    val dispatcher = TestCoroutineDispatcher()

    @Before
    fun setUp(){
        Dispatchers.setMain(dispatcher)
        fakeDatabaseRepository = FakeDatabaseRepository()
        savedPlacesVM = SavedPlacesViewModel(fakeDatabaseRepository)
        runBlocking {
            fakeDatabaseRepository.insertPlace(PlaceDetailResponse(
                name = "A",
                otm = "sdfgvbjhn",
                point = Point(lat = 15.5, lon = 25.8),
                rate = "3h",
                sources = Sources(attributes = listOf(""), geometry = "asd"),
                xid = "aaa"
            ))
        }

    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `load saved places, correct`() = runBlocking{
        savedPlacesVM.loadSavedPlaces()
        assertEquals(savedPlacesVM.places, listOf(PlaceDetailResponse(
            name = "A",
            otm = "sdfgvbjhn",
            point = Point(lat = 15.5, lon = 25.8),
            rate = "3h",
            sources = Sources(attributes = listOf(""), geometry = "asd"),
            xid = "aaa"
        )))
    }



}