package cz.mendelu.xspacek6.va2.travelApp.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import cz.mendelu.xspacek6.va2.travelApp.R
import cz.mendelu.xspacek6.va2.travelApp.models.ScreenState
import cz.mendelu.xspacek6.va2.travelApp.navigation.INavigationRouter
import cz.mendelu.xspacek6.va2.travelApp.ui.elements.BackArrowScreen
import cz.mendelu.xspacek6.va2.travelApp.ui.elements.ErrorScreen
import cz.mendelu.xspacek6.va2.travelApp.ui.elements.LoadingScreen
import org.koin.androidx.compose.getViewModel

@Composable
fun FilterScreen(
    navigation: INavigationRouter,
    viewModel: FilterScreenViewModel = getViewModel()
){

    val screenState: MutableState<ScreenState<Boolean>> = rememberSaveable{
        mutableStateOf(ScreenState.Loading())
    }

    viewModel.filterUiState.value.let {
        when(it){
            is FilterScreenUiStates.Start -> {
                LaunchedEffect(it) {
                    viewModel.loadPreferences()
                }
            }
            is FilterScreenUiStates.Success -> {
                screenState.value = ScreenState.DataLoaded(it.data)
            }
        }

    }

    BackArrowScreen(
        topBarText = stringResource(R.string.filters),
        content = {
            FilterScreenContent(
                navigation = navigation,
                viewModel = viewModel,
                screenState = screenState.value
            )
        },
        actions = {

        },
        onBackClick = { navigation.returnBack() }
    )

}

@Composable
fun FilterScreenContent(
    navigation: INavigationRouter,
    viewModel: FilterScreenViewModel,
    screenState: ScreenState<Boolean>
){

    screenState.let {
        when(it){
            is ScreenState.DataLoaded -> Filters(
                navigation = navigation,
                viewModel = viewModel
            )
            is ScreenState.Error -> ErrorScreen(text = stringResource(id = it.error))
            is ScreenState.Loading -> LoadingScreen()

        }
    }

}

@Composable
fun Filters(
    navigation: INavigationRouter,
    viewModel: FilterScreenViewModel
){



    var radiusSlider by remember { mutableStateOf(viewModel.radius.toFloat()) }
    var limitSlider by remember { mutableStateOf(viewModel.limit.toFloat()) }

    Column {

        Text(text = stringResource(id = R.string.radius) + ": ${radiusSlider.toInt()} m", style = MaterialTheme.typography.subtitle1)
        Slider(value = radiusSlider, onValueChange = { radiusSlider = it }, steps = 0, valueRange = 100f..5000f, enabled = true )

        Spacer(modifier = Modifier.padding(16.dp))

        Text(text = stringResource(id = R.string.limit) + ": ${limitSlider.toInt()}", style = MaterialTheme.typography.subtitle1)
        Slider(value = limitSlider, onValueChange = { limitSlider = it }, steps = 0, valueRange = 1f..200f, enabled = true )

        Spacer(modifier = Modifier.padding(16.dp))

        OutlinedButton(onClick = {
            viewModel.savePreferences(
                radius = radiusSlider.toInt(),
                limit = limitSlider.toInt()
            )
            navigation.returnBack()
        }) {
            Text(stringResource(id = R.string.save))
        }

    }

}