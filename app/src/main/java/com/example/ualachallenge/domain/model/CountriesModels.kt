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

fun List<Country>.filterCountries(query: String, filterFavorites: Boolean): List<Country> {
    if (query.isBlank()) return if (filterFavorites) filter { it.isFavorite } else this

    val lowerQuery = query.lowercase()

    val start = lowerBound(lowerQuery)
    val end = upperBound(lowerQuery)

    if (start >= end) return emptyList()

    return subList(start, end).filterFavoritesIf(filterFavorites)
}

private fun List<Country>.lowerBound(prefix: String): Int {
    var low = 0
    var high = this.size
    while (low < high) {
        val mid = (low + high) / 2
        val name = this[mid].name.lowercase()
        if (name < prefix) {
            low = mid + 1
        } else {
            high = mid
        }
    }
    return low
}

private fun List<Country>.upperBound(prefix: String): Int {
    var low = 0
    var high = this.size
    while (low < high) {
        val mid = (low + high) / 2
        val name = this[mid].name.lowercase()
        if (name.startsWith(prefix)) {
            low = mid + 1
        } else if (name < prefix) {
            low = mid + 1
        } else {
            high = mid
        }
    }
    return low
}

private fun List<Country>.filterFavoritesIf(shouldFilter: Boolean): List<Country> {
    return if (shouldFilter) filter { it.isFavorite } else this
}

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
