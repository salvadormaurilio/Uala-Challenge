package com.example.ualachallenge.ui.home

import androidx.compose.runtime.Composable
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.example.ualachallenge.ui.countries.CountriesScreen
import com.example.ualachallenge.ui.country.CountryDetailScreen
import com.example.ualachallenge.ui.map.CountryMapScreen

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
        CountriesScreen(navigationToCountryRoute = {
            navController.navigate(it)
        })
    }
}

private fun NavGraphBuilder.countryMap(navController: NavHostController) {
    composable<CountryRoutes.CountryMap> { backStackEntry ->
        val countryMap: CountryRoutes.CountryMap = backStackEntry.toRoute()
        CountryMapScreen(
            countryMap = countryMap,
            onBack = { navController.popBackStack() }
        )
    }
}

private fun NavGraphBuilder.countryDetailNav(navController: NavHostController) {
    composable<CountryRoutes.CountryDetail> { backStackEntry ->
        val countryMap: CountryRoutes.CountryDetail = backStackEntry.toRoute()
        CountryDetailScreen(
            countryDetail = countryMap,
            onBack = { navController.popBackStack() }
        )
    }
}
