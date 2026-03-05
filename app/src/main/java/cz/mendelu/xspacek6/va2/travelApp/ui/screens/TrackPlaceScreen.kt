package cz.mendelu.xspacek6.va2.travelApp.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.material3.Button
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.*
import cz.mendelu.xspacek6.va2.travelApp.R
import cz.mendelu.xspacek6.va2.travelApp.models.ScreenState
import cz.mendelu.xspacek6.va2.travelApp.models.placeDetail.PlaceDetailResponse
import cz.mendelu.xspacek6.va2.travelApp.navigation.INavigationRouter
import cz.mendelu.xspacek6.va2.travelApp.ui.elements.BackArrowScreen
import cz.mendelu.xspacek6.va2.travelApp.ui.elements.ErrorScreen
import cz.mendelu.xspacek6.va2.travelApp.ui.elements.LoadingScreen
import org.koin.androidx.compose.getViewModel

@Composable
fun TrackPlaceScreen(navigation: INavigationRouter,
                     id: String,
                     viewModel: TrackPlaceViewModel = getViewModel()
) {

    viewModel.placeId = id

    val screenState: MutableState<ScreenState<PlaceDetailResponse>> = rememberSaveable {
        mutableStateOf(ScreenState.Loading())
    }

    viewModel.trackPlaceUiState.value.let {
        when(it) {
            is PlaceDetailUiState.Error -> {
                println("TrackPlace Error")
                screenState.value = ScreenState.Error(it.error)
            }
            is PlaceDetailUiState.Start -> {
                println("TrackPlace Start")
                LaunchedEffect(it) {
                    viewModel.loadPlace()
                }
            }
            is PlaceDetailUiState.Success -> {
                println("TrackPlace Success")
                screenState.value = ScreenState.DataLoaded(it.data)
            }
        }
    }

    BackArrowScreen(
        topBarText = stringResource(id = R.string.tracking_screen),
        disablePadding = true,
        drawFullScreenContent = true,
        content = {
            TrackPlaceScreenContent(
                navigation = navigation,
                viewModel = viewModel,
                screenState = screenState.value
            )
        },
        onBackClick = { navigation.returnBack() }
    )

}


@Composable
fun TrackPlaceScreenContent(
    navigation: INavigationRouter,
    viewModel: TrackPlaceViewModel,
    screenState: ScreenState<PlaceDetailResponse>
) {

    Box(Modifier.fillMaxSize()) {
        screenState.let {
            when (it) {
                is ScreenState.DataLoaded -> TrackMap(
                    navigation = navigation,
                    viewModel = viewModel,
                    place = it.data
                )
                is ScreenState.Error -> ErrorScreen(text = stringResource(id = it.error))
                is ScreenState.Loading -> LoadingScreen()

            }
        }
    }

}


@Composable
fun TrackMap(
    navigation: INavigationRouter,
    viewModel: TrackPlaceViewModel,
    place: PlaceDetailResponse
) {

    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(
            LatLng(place.point.lat, place.point.lon),
            16f
        )
    }

    Box(Modifier.fillMaxSize()) {
        GoogleMap(
            modifier = Modifier.fillMaxHeight(),
            cameraPositionState = cameraPositionState
        ) {

            Marker(
                state = MarkerState(position = LatLng(place.point.lat, place.point.lon)),
                title = place.name
            )

            Circle(
                center = LatLng(place.point.lat, place.point.lon),
                radius = 100.0,
                strokeColor = colorResource(id = R.color.purple_500),
                fillColor = colorResource(id = R.color.purple_200T)
            )

        }

        Button(
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    start = 64.dp,
                    end = 64.dp,
                    bottom = 16.dp
                )
                .align(Alignment.BottomCenter),
            onClick = {
                viewModel.savePlace()
                navigation.returnBack()
            }) {
            Text(text = stringResource(id = R.string.collect))
        }
    }


}
