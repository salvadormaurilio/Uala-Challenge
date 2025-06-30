package com.example.ualachallenge.data.datasource.remote.retrofit

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
