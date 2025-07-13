package com.example.ualachallenge.ui.home

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.navigation.compose.rememberNavController
import com.example.ualachallenge.ui.theme.CountriesChallengeTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CountriesActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            CountriesChallengeTheme {
                val navHostController = rememberNavController()
                HomeNavHost(navHostController = navHostController)
            }
        }
    }
}
