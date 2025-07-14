package com.example.ualachallenge.ui.country

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBox
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.ualachallenge.R
import com.example.ualachallenge.core.extensions.empty
import com.example.ualachallenge.ui.countries.givenCountryDetailRouteTestData
import com.example.ualachallenge.ui.home.CountryRoutes
import com.example.ualachallenge.ui.theme.CountriesChallengeTheme
import com.example.ualachallenge.ui.views.CountryTopAppBar
import com.example.ualachallenge.ui.views.TextWithIcon

@Composable
fun CountryDetailScreen(countryDetail: CountryRoutes.CountryDetail, onBack: () -> Unit = {}) {
    Scaffold(
        topBar = {
            CountryTopAppBar(
                title = stringResource(R.string.country_name, countryDetail.name, countryDetail.country),
                onBack = onBack
            )
        },
        content = { paddingValues ->
            CountryDetail(
                modifier = Modifier.padding(paddingValues), countryDetail
            )
        })
}

@Composable
private fun CountryDetail(
    modifier: Modifier = Modifier,
    countryDetail: CountryRoutes.CountryDetail
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(16.dp)
            .verticalScroll(rememberScrollState())
    ) {
        Row {
            AsyncImage(
                modifier = Modifier
                    .width(175.dp)
                    .height(350.dp)
                    .clip(RoundedCornerShape(12.dp)),
                contentScale = ContentScale.Crop,
                contentDescription = String.empty(),
                model = countryDetail.image
            )

            Spacer(modifier = Modifier.width(12.dp))

            Column(
                modifier = Modifier.padding(vertical = 16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp),
            ) {

                TextWithIcon(
                    icon = Icons.Default.AccountBox,
                    text = stringResource(R.string.country_id, countryDetail.id)
                )

                TextWithIcon(
                    icon = Icons.Default.Info,
                    text = stringResource(R.string.country_name, countryDetail.name, countryDetail.country)
                )

                TextWithIcon(
                    icon = Icons.Default.LocationOn,
                    text = stringResource(R.string.country_location, countryDetail.latitude, countryDetail.longitude),
                )
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        Text(
            text = loremIpsum(),
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.onPrimary
        )
    }
}

fun loremIpsum(paragraphs: Int = 1): String {
    val base = "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua."
    return (1..paragraphs).joinToString("\n\n") { base }
}

@Preview(showBackground = true)
@Composable
fun CountryDetailContentUiStateSuccessPreview() {
    CountriesChallengeTheme {
        CountryDetailScreen(
            countryDetail = givenCountryDetailRouteTestData()
        )
    }
}
