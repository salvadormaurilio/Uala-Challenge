package com.example.ualachallenge.data.datasource.local

import com.example.ualachallenge.data.datasource.local.room.CountryEntity
import kotlinx.coroutines.flow.Flow

interface CountriesLocalDataSource {

    suspend fun insertCountries(countries: List<CountryEntity>)

    fun getCountries(): Flow<Result<List<CountryEntity>>>
}
