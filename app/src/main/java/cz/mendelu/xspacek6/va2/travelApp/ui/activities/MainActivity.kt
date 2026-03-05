package cz.mendelu.xspacek6.va2.travelApp.ui.activities

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.content.ContextCompat.startActivity
import androidx.lifecycle.lifecycleScope
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import cz.mendelu.xspacek6.va2.travelApp.hasCameraPermission
import cz.mendelu.xspacek6.va2.travelApp.hasLocationPermission
import cz.mendelu.xspacek6.va2.travelApp.map.LocationClient
import cz.mendelu.xspacek6.va2.travelApp.navigation.Destination
import cz.mendelu.xspacek6.va2.travelApp.navigation.NavGraph
import cz.mendelu.xspacek6.va2.travelApp.ui.theme.TravelAppTheme
import org.koin.androidx.viewmodel.ext.android.viewModel

@OptIn(ExperimentalFoundationApi::class)
class MainActivity : ComponentActivity() {

    private val viewModel: MainActivityViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        lifecycleScope.launchWhenResumed {
            viewModel.mainActivityScreenState.collect { value ->
                when (value) {
                    is MainActivityUiState.Default -> {
                        viewModel.checkAppState()
                    }
                    is MainActivityUiState.ContinueToApp -> {
                        setContent {
                            TravelAppTheme {
                                // A surface container using the 'background' color from the theme
                                Surface(
                                    modifier = Modifier.fillMaxSize(),
                                    color = MaterialTheme.colorScheme.background
                                ) {
                                    NavGraph(startDestination = Destination.ListOfPlacesScreen.route)
                                }
                            }
                        }
                    }
                    is MainActivityUiState.RunForAFirstTime -> {
                        viewModel.setToContinue()

                        val intent = Intent(this@MainActivity, AppIntroActivity::class.java)
                        startActivity(intent)


                    }
                }
            }
        }

    }



}


@Composable
fun Greeting(name: String) {
    Text(text = "Hello $name!")
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    TravelAppTheme {
        Greeting("Android")
    }
}