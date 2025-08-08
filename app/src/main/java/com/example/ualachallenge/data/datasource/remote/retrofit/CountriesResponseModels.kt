package com.example.ualachallenge.data.datasource.remote.retrofit

import com.example.ualachallenge.core.extensions.orDefault
import com.example.ualachallenge.domain.model.Country
import com.google.gson.annotations.SerializedName

data class CountryResponse(
    @SerializedName("_id")
    val id: Int?,
    @SerializedName("name")
    val name: String?,
    @SerializedName("country")
    val country: String?,
    @SerializedName("coord")
    val coordinates: CountryCoordinatesResponse?
)

data class CountryCoordinatesResponse(
    @SerializedName("lon")
    val longitude: Double?,
    @SerializedName("lat")
    val latitude: Double?,
)

fun Result<List<CountryResponse>>.toCountries() = map { it.sortedByName().toCountries() }

private fun List<CountryResponse>.sortedByName() = sortedWith(
    compareBy<CountryResponse, String>(String.CASE_INSENSITIVE_ORDER) { it.name.orEmpty() }
        .thenBy(String.CASE_INSENSITIVE_ORDER) { it.country.orEmpty() }
)

private fun List<CountryResponse>.toCountries() = map { it.toCountry() }

private fun CountryResponse.toCountry() = Country(
    id = id.orDefault(),
    name = name.orEmpty(),
    country = country.orEmpty(),
    latitude = coordinates?.latitude.orDefault(),
    longitude = coordinates?.longitude.orDefault()
)
