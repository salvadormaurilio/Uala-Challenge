package com.example.ualachallenge.data

import com.example.ualachallenge.domain.model.Country
import kotlinx.coroutines.flow.Flow

interface CountriesRepository {

    fun getCountries(): Flow<Result<List<Country>>>

    suspend fun updateFavorite(id: Int, isFavorite: Boolean)
}
