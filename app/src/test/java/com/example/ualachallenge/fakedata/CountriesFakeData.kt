package com.example.ualachallenge.fakedata

import com.example.ualachallenge.data.datasource.local.room.CountryEntity
import com.example.ualachallenge.data.datasource.remote.retrofit.CountryCoordinatesResponse
import com.example.ualachallenge.data.datasource.remote.retrofit.CountryResponse
import com.example.ualachallenge.domain.model.Country

const val EXPECT_COUNTRY_ENDPOINT = "/dce8843a8edbe0b0018b32e137bc2b3a/raw/0996accf70cb0ca0e16f9a99e0ee185fafca7af1/cities.json"
const val ANY_QUERY = "nov"

fun givenCountriesResponseFakeData() = listOf(
    CountryResponse(
        id = 1283378,
        name = "Gorkhā",
        country = "NP",
        coordinates = CountryCoordinatesResponse(
            longitude = 84.633331,
            latitude = 28.0,
        )
    ),
    CountryResponse(
        id = 707860,
        name = "Hurzuf",
        country = "UA",
        coordinates = CountryCoordinatesResponse(
            longitude = 34.283333,
            latitude = 44.549999
        ),
    ),
    CountryResponse(
        id = 519188,
        name = "Novinki",
        country = "RU",
        coordinates = CountryCoordinatesResponse(
            longitude = 37.666668,
            latitude = 55.683334
        ),
    )
)

fun givenCountriesEntityFakeData() = listOf(
    CountryEntity(
        id = 1283378,
        name = "Gorkhā",
        country = "NP",
        longitude = 84.633331,
        latitude = 28.0,
    ),
    CountryEntity(
        id = 707860,
        name = "Hurzuf",
        country = "UA",
        longitude = 34.283333,
        latitude = 44.549999
    ),
    CountryEntity(
        id = 519188,
        name = "Novinki",
        country = "RU",
        longitude = 37.666668,
        latitude = 55.683334
    )
)

fun givenCountriesFakeData() = listOf(
    Country(
        id = 1283378,
        name = "Gorkhā",
        country = "NP",
        longitude = 84.633331,
        latitude = 28.0,
    ),
    Country(
        id = 707860,
        name = "Hurzuf",
        country = "UA",
        longitude = 34.283333,
        latitude = 44.549999
    ),
    Country(
        id = 519188,
        name = "Novinki",
        country = "RU",
        longitude = 37.666668,
        latitude = 55.683334
    )
)

fun givenFilterCountriesFakeData() = listOf(
    Country(
        id = 519188,
        name = "Novinki",
        country = "RU",
        longitude = 37.666668,
        latitude = 55.683334
    )
)

