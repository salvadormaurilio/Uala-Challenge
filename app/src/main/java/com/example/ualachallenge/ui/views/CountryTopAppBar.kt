package com.example.ualachallenge.ui.views

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.example.ualachallenge.core.extensions.empty
import com.example.ualachallenge.ui.theme.CountriesChallengeTheme

@Composable
@OptIn(ExperimentalMaterial3Api::class)
fun CountryTopAppBar(title: String, onBack: () -> Unit) {
    TopAppBar(
        colors = TopAppBarDefaults.topAppBarColors(containerColor = MaterialTheme.colorScheme.primary),
        title = {
            Text(
                text = title,
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
}

@Composable
@Preview(showBackground = true)
fun CountryTopAppBarPreview() {
    CountriesChallengeTheme {
        CountryTopAppBar(
            title = "Gorkhā, NP",
            onBack = {}
        )
    }
}
