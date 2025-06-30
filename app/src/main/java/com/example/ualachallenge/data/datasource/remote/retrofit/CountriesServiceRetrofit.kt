package com.example.ualachallenge.data.datasource.remote.retrofit

import retrofit2.http.GET

interface CountriesServiceRetrofit {

    @GET(COUNTRIES_ENDPOINT)
    suspend fun fetchCountries(): List<CountryResponse>
}
