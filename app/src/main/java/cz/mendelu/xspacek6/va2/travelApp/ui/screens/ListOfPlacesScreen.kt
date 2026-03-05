package cz.mendelu.xspacek6.va2.travelApp.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.TopAppBar
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cz.mendelu.xspacek6.va2.travelApp.R
import cz.mendelu.xspacek6.va2.travelApp.models.ScreenState
import cz.mendelu.xspacek6.va2.travelApp.models.places.Feature
import cz.mendelu.xspacek6.va2.travelApp.models.places.PlacesResponse
import cz.mendelu.xspacek6.va2.travelApp.navigation.INavigationRouter
import cz.mendelu.xspacek6.va2.travelApp.ui.elements.ErrorScreen
import cz.mendelu.xspacek6.va2.travelApp.ui.elements.LoadingScreen
import cz.mendelu.xspacek6.va2.travelApp.ui.theme.Purple40
import org.koin.androidx.compose.getViewModel
import kotlin.math.roundToInt

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ListOfPlacesScreen(navigation: INavigationRouter,
                       viewModel: ListOfPlacesViewModel = getViewModel()
) {

    val screenState: MutableState<ScreenState<PlacesResponse>> = rememberSaveable {
        mutableStateOf(ScreenState.Loading())
    }

    viewModel.placesUiState.value.let {
        when(it){
            is ListOfPlacesUiState.Error -> {
                screenState.value = ScreenState.Error(it.error)
            }
            is ListOfPlacesUiState.Start -> {
                println("LISTOFPLACESUISTATE.START()")
                screenState.value = ScreenState.Loading()
                LaunchedEffect(it) {
                    //viewModel.getLatLon()
                    viewModel.loadPlaces()
                }
            }
            is ListOfPlacesUiState.Success -> {
                screenState.value = ScreenState.DataLoaded(it.data)
            }
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .wrapContentWidth(align = Alignment.CenterHorizontally)
                    ) {
                        Text(
                            text = stringResource(id = R.string.app_name),
                            style = MaterialTheme.typography.labelMedium,
                            color = Color.Black,
                            modifier = Modifier
                                .padding(start = 0.dp)
                                .weight(1.5f)
                        )
                    }
                },
                actions = {
                    IconButton(onClick = {
                        viewModel.placesUiState.value = ListOfPlacesUiState.Start()
                    }) {
                        androidx.compose.material.Icon(
                            painter = painterResource(R.drawable.refresh_24),
                            contentDescription = null,
                            tint = Color.Black
                        )
                    }
                    IconButton(onClick = {
                        navigation.navigateToFilterScreen()
                    }) {
                        androidx.compose.material.Icon(
                            painter = painterResource(R.drawable.filter_24),
                            contentDescription = null,
                            tint = Color.Black
                        )
                    }
                },
                elevation = 0.dp,
                backgroundColor = Purple40
            )
        },
        content = {
//            PlacesListScreenContent(
//                paddingValues = it,
//                navigation = navigation,
//                screenState = screenState.value
//            )
            Column(modifier = Modifier.padding(it)) {
                Tabs(
                    paddingValues = it,
                    navigation = navigation,
                    screenState = screenState.value,
                    viewModel = viewModel
                )
            }
        },
    )
}


@Composable
fun Tabs( paddingValues: PaddingValues,
          navigation: INavigationRouter,
          viewModel: ListOfPlacesViewModel,
          screenState: ScreenState<PlacesResponse>) {
    
    var tabIndex by remember { mutableStateOf(1) }
    val tabTitles = listOf("Saved", "List", "Map")
    Column {
        TabRow(selectedTabIndex = tabIndex) {
            tabTitles.forEachIndexed { index, title ->
                Tab(selected = tabIndex == index,
                    onClick = { tabIndex = index },
                    text = { Text(text = title) })
            }
        }
        when (tabIndex) {
            0 ->
                SavedPlacesScreen(
                paddingValues = paddingValues,
                navigation = navigation
            )
            1 -> PlacesListScreenContent(
                paddingValues = paddingValues,
                navigation = navigation,
                screenState = screenState
            )
            2 -> MapScreenContent(
                paddingValues = paddingValues,
                navigation = navigation,
                viewModel = viewModel,
                screenState = screenState
            )
        }
    }
}


@Composable
fun PlacesListScreenContent(
    paddingValues: PaddingValues,
    navigation: INavigationRouter,
    screenState: ScreenState<PlacesResponse>){

    screenState.let {
        when(it){
            is ScreenState.DataLoaded -> PlacesList(
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
fun PlacesList(
    paddingValues: PaddingValues,
    navigation: INavigationRouter,
    places: PlacesResponse
) {

    LazyColumn(modifier = Modifier.padding(paddingValues)) {
        places.features.forEach{
            item(key = it.properties.xid) {
                PlaceRow(
                    place = it,
                    onRowClick = {
                        navigation.navigateToPlaceDetail(it.properties.xid)
                    }
                )
            }
        }
    }

}


@Composable
fun PlaceRow(
    place: Feature,
    onRowClick: () -> Unit) {

    Row(verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .clickable(onClick = onRowClick)) {

        Column {
            place.properties.let {
                Text(text = it.name,
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier
                        .padding(start = 6.dp)
                        .fillMaxWidth(0.8f)
                )
            }

        }

        Spacer(modifier = Modifier.weight(1.0F))

        Column {
            Text(text = "${place.properties.dist.roundToInt()} m",
                fontSize = 14.sp)
        }

    }
}