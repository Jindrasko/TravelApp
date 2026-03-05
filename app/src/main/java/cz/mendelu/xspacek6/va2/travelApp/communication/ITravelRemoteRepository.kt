package cz.mendelu.xspacek6.va2.travelApp.communication

import cz.mendelu.xspacek6.va2.travelApp.architecture.CommunicationResult
import cz.mendelu.xspacek6.va2.travelApp.architecture.IBaseRemoteRepository
import cz.mendelu.xspacek6.va2.travelApp.models.placeDetail.PlaceDetailResponse
import cz.mendelu.xspacek6.va2.travelApp.models.places.PlacesResponse
import retrofit2.http.Path
import retrofit2.http.Query

interface ITravelRemoteRepository : IBaseRemoteRepository {

    suspend fun places(
        @Query("radius") radius: Int,
        @Query("lon") longitude: Double,
        @Query("lat") latitude: Double,
        @Query("kinds") categories: String,
        @Query("limit") limit: Int,
        @Query("rate") rate: String,
        @Query("apikey") api: String
    ) : CommunicationResult<PlacesResponse>

    suspend fun place(
        @Path("xid") xId: String,
        @Query("apikey") api: String
    ) : CommunicationResult<PlaceDetailResponse>

}