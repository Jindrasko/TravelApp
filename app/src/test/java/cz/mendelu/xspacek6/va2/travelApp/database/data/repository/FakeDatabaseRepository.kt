package cz.mendelu.xspacek6.va2.travelApp.database.data.repository

import cz.mendelu.xspacek6.va2.travelApp.database.IPlaceLocalRepository
import cz.mendelu.xspacek6.va2.travelApp.models.placeDetail.PlaceDetailResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class FakeDatabaseRepository: IPlaceLocalRepository {

    private val places = mutableListOf<PlaceDetailResponse>()

    override fun getAll(): Flow<List<PlaceDetailResponse>> {
        return flow { emit(places) }
    }

    override suspend fun getList(): List<PlaceDetailResponse> {
        return places
    }

    override suspend fun findById(id: String): PlaceDetailResponse? {
        return places.find { it.xid == id }
    }

    override suspend fun insertPlace(place: PlaceDetailResponse) {
        places.add(place)
    }

    override suspend fun deletePlace(place: PlaceDetailResponse) {
        places.remove(place)
    }

}