package com.example.ualachallenge.data.datasource.remote

import com.example.ualachallenge.data.datasource.remote.retrofit.CountryResponse
import kotlinx.coroutines.flow.Flow

interface CountriesRemoteDataSource {

    fun fetchCountries(): Flow<Result<List<CountryResponse>>>
}
