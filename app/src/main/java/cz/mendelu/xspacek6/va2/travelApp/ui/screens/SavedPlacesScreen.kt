package cz.mendelu.xspacek6.va2.travelApp.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cz.mendelu.xspacek6.va2.travelApp.models.ScreenState
import cz.mendelu.xspacek6.va2.travelApp.models.placeDetail.PlaceDetailResponse
import cz.mendelu.xspacek6.va2.travelApp.navigation.INavigationRouter
import cz.mendelu.xspacek6.va2.travelApp.ui.elements.ErrorScreen
import cz.mendelu.xspacek6.va2.travelApp.ui.elements.LoadingScreen
import org.koin.androidx.compose.getViewModel


@Composable
fun SavedPlacesScreen(
    paddingValues: PaddingValues,
    navigation: INavigationRouter,
    viewModel: SavedPlacesViewModel = getViewModel()
){

    val screenState: MutableState<ScreenState<List<PlaceDetailResponse>>> = rememberSaveable {
        mutableStateOf(ScreenState.Loading())
    }

    viewModel.savedPlacesUiState.value.let {
        when(it){
            is SavedPlacesUiState.Error -> {
                screenState.value = ScreenState.Error(it.error)
            }
            is SavedPlacesUiState.Start -> {
                LaunchedEffect(it) {
                    viewModel.loadSavedPlaces()
                }
            }
            is SavedPlacesUiState.Success -> {
                screenState.value = ScreenState.DataLoaded(it.data)
            }
        }
    }

    SavedPlacesScreenContent(paddingValues = paddingValues, navigation = navigation, screenState = screenState.value)

}

@Composable
fun SavedPlacesScreenContent(paddingValues: PaddingValues ,navigation: INavigationRouter, screenState: ScreenState<List<PlaceDetailResponse>>){

    screenState.let {
        when(it){
            is ScreenState.DataLoaded -> SavedPlacesList(
                paddingValues = paddingValues,
                navigation = navigation,
                places = it.data
            )
            is ScreenState.Error -> ErrorScreen(text = stringResource(id = it.error))
            is ScreenState.Loading -> LoadingScreen()
        }
    }

}

@Composable
fun SavedPlacesList(
    paddingValues: PaddingValues,
    navigation: INavigationRouter,
    places: List<PlaceDetailResponse>
) {

    LazyColumn(modifier = Modifier.padding(paddingValues)) {
        places.forEach {
            item(key = it.xid) {
                SavedPlaceRow(
                    place = it,
                    onRowClick = {
                        navigation.navigateToPlaceDetail(it.xid)
                    }
                )
            }
        }
    }

}


@Composable
fun SavedPlaceRow(place: PlaceDetailResponse, onRowClick: () -> Unit){

    Row(verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .clickable(onClick = onRowClick)) {

        Column {
            Text(text = place.name,
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier
                    .padding(start = 6.dp)
                    .fillMaxWidth(0.8f)
            )

        }

        Spacer(modifier = Modifier.weight(1.0F))

        Column {
            if (place.done){
                Icon(imageVector = Icons.Filled.Check, contentDescription = "Favorite")
            } else if (place.favorte){
                Icon(imageVector = Icons.Filled.Star, contentDescription = "Favorite")
            }
        }

    }

}
