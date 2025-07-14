package com.example.ualachallenge.ui.map

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.example.ualachallenge.R
import com.example.ualachallenge.core.extensions.empty
import com.example.ualachallenge.ui.countries.givenCountryMapRouteTestData
import com.example.ualachallenge.ui.home.CountryRoutes
import com.example.ualachallenge.ui.theme.CountriesChallengeTheme
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerState
import com.google.maps.android.compose.rememberCameraPositionState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CountryMapScreen(countryMap: CountryRoutes.CountryMap, onBack: () -> Unit = {}) {
    Scaffold(
        topBar = {
            TopAppBar(
                colors = TopAppBarDefaults.topAppBarColors(containerColor = MaterialTheme.colorScheme.primary),
                title = {
                    Text(
                        text = stringResource(R.string.country_name, countryMap.name, countryMap.country),
                        style = MaterialTheme.typography.titleLarge,
                        color = MaterialTheme.colorScheme.onBackground
                    )
                },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            tint = MaterialTheme.colorScheme.onBackground,
                            contentDescription = String.empty()
                        )
                    }
                }
            )
        },
        content = { paddingValues ->
            CountryMapContainer(
                modifier = Modifier.padding(paddingValues),
                countryMap = countryMap
            )
        }
    )
}

@Composable
fun CountryMapContainer(modifier: Modifier, countryMap: CountryRoutes.CountryMap? = null) {
    if (countryMap == null) return

    val countryPosition = LatLng(countryMap.latitude, countryMap.longitude)
    val cameraPositionState = rememberCameraPositionState { position = CameraPosition.fromLatLngZoom(countryPosition, 10f) }
    val markerState = remember { MarkerState(position = LatLng(countryMap.latitude, countryMap.longitude)) }

    GoogleMap(
        modifier = modifier.fillMaxSize(),
        cameraPositionState = cameraPositionState
    ) {
        Marker(
            state = markerState,
            title = countryMap.name,
            snippet = countryMap.country
        )
    }
}

@Preview(showBackground = true)
@Composable
fun CountryMapScreenPreview() {
    CountriesChallengeTheme {
        CountryMapScreen(countryMap = givenCountryMapRouteTestData())
    }
}
