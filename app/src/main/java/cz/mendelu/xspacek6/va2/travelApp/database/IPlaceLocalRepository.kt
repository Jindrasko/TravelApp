package cz.mendelu.xspacek6.va2.travelApp.database

import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import cz.mendelu.xspacek6.va2.travelApp.models.placeDetail.PlaceDetailResponse
import kotlinx.coroutines.flow.Flow

interface IPlaceLocalRepository {

    fun getAll(): Flow<List<PlaceDetailResponse>>
    suspend fun getList(): List<PlaceDetailResponse>
    suspend fun findById(id : String): PlaceDetailResponse?
    suspend fun insertPlace(place: PlaceDetailResponse)
    suspend fun deletePlace(place: PlaceDetailResponse)
}