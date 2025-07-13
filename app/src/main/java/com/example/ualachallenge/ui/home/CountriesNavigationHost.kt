package com.example.ualachallenge.ui.home

import androidx.compose.runtime.Composable
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.example.ualachallenge.ui.countries.CountriesScreen

@Composable
fun HomeNavHost(navHostController: NavHostController) {
    NavHost(
        navController = navHostController,
        startDestination = CountryRoutes.Countries
    ) {
        countriesNav(navController = navHostController)
        countryMap(navController = navHostController)
        countryDetailNav(navController = navHostController)
    }
}

private fun NavGraphBuilder.countriesNav(navController: NavHostController) {
    composable<CountryRoutes.Countries> {
        CountriesScreen(navitaToCountryRoute = {
            navController.navigate(it)
        })
    }
}

private fun NavGraphBuilder.countryMap(navController: NavHostController) {
    composable<CountryRoutes.CountryMap> { backStackEntry ->
        val countryMap: CountryRoutes.CountryMap = backStackEntry.toRoute()
    }
}

private fun NavGraphBuilder.countryDetailNav(navController: NavHostController) {
    composable<CountryRoutes.CountryDetail> { backStackEntry ->
        val countryMap: CountryRoutes.CountryDetail = backStackEntry.toRoute()
    }
}
