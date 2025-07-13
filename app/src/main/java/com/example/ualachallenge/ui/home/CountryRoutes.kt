package com.example.ualachallenge.ui.home

import kotlinx.serialization.Serializable

sealed class CountryRoutes {
    @Serializable
    data object Countries : CountryRoutes()

    @Serializable
    data class CountryMap(
        val name: String,
        val country: String,
        val longitude: Double,
        val latitude: Double,
    ) : CountryRoutes()

    @Serializable
    data class CountryDetail(
        val id: Int,
        val name: String,
        val country: String,
        val longitude: Double,
        val latitude: Double,
    ) : CountryRoutes()
}
