package com.example.ualachallenge.ui.countries

import com.example.ualachallenge.BuildConfig
import com.example.ualachallenge.domain.model.Country
import com.example.ualachallenge.ui.home.CountryRoutes

fun getCountriesTestData() = listOf(
    getCountry1TestData(),
    getCountry2TestData(),
    getCountry3TestData(),
)

fun getCountry1TestData() = Country(
    id = 1283378,
    name = "Gorkhā",
    country = "NP",
    longitude = 84.633331,
    latitude = 28.0
)

fun getCountry2TestData() = Country(
    id = 707860,
    name = "Hurzuf",
    country = "UA",
    longitude = 34.283333,
    latitude = 44.549999,
    isFavorite = true
)

fun getCountry3TestData() = Country(
    id = 519188,
    name = "Novinki",
    country = "RU",
    longitude = 37.666668,
    latitude = 55.683334
)

fun givenCountryMapRouteTestData() = CountryRoutes.CountryMap(
    name = "Gorkhā",
    country = "NP",
    longitude = 84.633331,
    latitude = 28.0
)

fun givenCountryDetailRouteTestData() = CountryRoutes.CountryDetail(
    id = 1283378,
    name = "Gorkhā",
    country = "NP",
    longitude = 84.633331,
    latitude = 28.0,
    image = "https://maps.googleapis.com/maps/api/staticmap?center=28.0,84.633331&zoom=14&size=300x600&key=${BuildConfig.MAPS_API_KEY}"
)
