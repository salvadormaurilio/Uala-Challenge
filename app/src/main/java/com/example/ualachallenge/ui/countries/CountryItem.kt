package com.example.ualachallenge.ui.countries

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.ualachallenge.R
import com.example.ualachallenge.domain.model.Country
import com.example.ualachallenge.ui.theme.CountriesChallengeTheme

@Composable
fun CountryItem(
    country: Country,
    onShowMap: (Country) -> Unit = {},
    onShowDetail: (Country) -> Unit = {},
    onFavorite: (Int, Boolean) -> Unit = { _, _ -> }
) {
    Card(
        modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        elevation = CardDefaults.cardElevation(defaultElevation = 6.dp),
        onClick = { onShowMap(country) }
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
        ) {
            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(12.dp),
            ) {
                Text(
                    text = stringResource(R.string.country_name, country.name, country.country),
                    style = MaterialTheme.typography.titleLarge,
                    color = MaterialTheme.colorScheme.onPrimary
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = stringResource(R.string.coordinates, country.latitude.toString(), country.longitude.toString()),
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.onPrimary,
                )
            }

            Column(modifier = Modifier.padding(4.dp)) {
                IconButton(onClick = { onFavorite(country.id, !country.isFavorite) }) {
                    Icon(
                        imageVector = if (country.isFavorite) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
                        contentDescription = null
                    )
                }

                IconButton(onClick = { onShowDetail(country) }) {
                    Icon(
                        imageVector = Icons.Default.Info,
                        contentDescription = null
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun CountryItemPreview() {
    CountriesChallengeTheme {
        Column {
            CountryItem(country = getCountry1TestData())
            CountryItem(country = getCountry2TestData())
        }
    }
}
