package com.example.ualachallenge.fakedata

import com.example.ualachallenge.data.datasource.local.room.CountryEntity

fun givenCountriesEntityFakeData() = listOf(
    CountryEntity(
        id = 519188,
        name = "Novinki",
        country = "RU",
        longitude = 37.666668,
        latitude = 55.683334
    ),
    CountryEntity(
        id = 707860,
        name = "Hurzuf",
        country = "UA",
        longitude = 34.283333,
        latitude = 44.549999
    ),
    CountryEntity(
        id = 1283378,
        name = "Gorkhā",
        country = "NP",
        longitude = 84.633331,
        latitude = 28.0,
    )
)
