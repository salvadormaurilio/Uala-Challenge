package com.example.ualachallenge.ui.countries

import com.example.ualachallenge.domain.model.Country

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
