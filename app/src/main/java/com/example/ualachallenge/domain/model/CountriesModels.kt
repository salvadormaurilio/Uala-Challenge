package com.example.ualachallenge.domain.model

import com.example.ualachallenge.BuildConfig
import com.example.ualachallenge.data.datasource.local.room.CountryEntity
import com.example.ualachallenge.ui.home.CountryRoutes

data class Country(
    val id: Int,
    val name: String,
    val country: String,
    val longitude: Double,
    val latitude: Double,
    val isFavorite: Boolean = false,
)

fun List<Country>.toCountriesEntity() = map { it.toCountryEntity() }

private fun Country.toCountryEntity() = CountryEntity(
    id = id,
    name = name,
    country = country,
    latitude = latitude,
    longitude = longitude,
)

fun Result<List<Country>>.isValidCountries() = isSuccess && !getOrNull().isNullOrEmpty()

fun Result<List<Country>>.getCountries() = getOrNull().orEmpty()

fun Result<List<Country>>.filterCountries(query: String, filterFavorites: Boolean) = map { it.filterCountries(query, filterFavorites) }

fun List<Country>.filterCountries(query: String, filterFavorites: Boolean) =
    filter { it.name.startsWith(query, ignoreCase = true) && (!filterFavorites || it.isFavorite) }

fun List<Country>.updateFavorite(id: Int, isFavorite: Boolean) = map { if (it.id == id) it.copy(isFavorite = isFavorite) else it }

fun Country.toCountryMapRoute() = CountryRoutes.CountryMap(
    name = name,
    country = country,
    longitude = longitude,
    latitude = latitude,
)

fun Country.toCountryDetailRoute() = CountryRoutes.CountryDetail(
    id = id,
    name = name,
    country = country,
    longitude = longitude,
    latitude = latitude,
    image = "https://maps.googleapis.com/maps/api/staticmap?center=$latitude,$longitude&zoom=14&size=300x600&key=${BuildConfig.MAPS_API_KEY}"
)
