package com.example.ualachallenge.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.example.ualachallenge.ui.countries.CountriesScreen
import com.example.ualachallenge.ui.theme.CountriesChallengeTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            CountriesChallengeTheme {
                CountriesScreen()
            }
        }
    }
}
