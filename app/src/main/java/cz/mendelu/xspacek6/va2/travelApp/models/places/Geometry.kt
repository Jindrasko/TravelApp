package cz.mendelu.xspacek6.va2.travelApp.models.places

import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.clustering.ClusterItem
import java.io.Serializable

data class Geometry(
    val coordinates: List<Double>,
    val type: String
)