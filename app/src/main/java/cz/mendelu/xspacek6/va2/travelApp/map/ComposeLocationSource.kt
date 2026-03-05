package cz.mendelu.xspacek6.va2.travelApp.map

import android.content.Context
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.LocationSource
import cz.mendelu.xspacek6.va2.travelApp.hasLocationPermission

class ComposeLocationSource(private val context: Context) : LocationSource {

    private val client = LocationServices.getFusedLocationProviderClient(context)
    private var listener: LocationSource.OnLocationChangedListener? = null

    override fun activate(listener: LocationSource.OnLocationChangedListener) {
        this.listener = listener
        if (!context.hasLocationPermission()) {
            return
        }
        client.lastLocation.addOnSuccessListener { location ->
            listener.onLocationChanged(location)
        }
    }


    override fun deactivate() {
        listener = null
    }
}