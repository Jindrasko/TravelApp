package cz.mendelu.xspacek6.va2.travelApp.map

import android.location.Location
import kotlinx.coroutines.flow.Flow

interface LocationClient {
    fun getLocationUpdates(interval: Long): Flow<Location>
    suspend fun getLastLocation(): Location
    fun getCurrentLocation(callback: (Location) -> Unit)

    class LocationException(message: String): Exception()
}