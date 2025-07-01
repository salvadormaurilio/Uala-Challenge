package com.example.ualachallenge.domain

import com.example.ualachallenge.domain.model.Country
import kotlinx.coroutines.flow.Flow

interface GetCountriesUseCase {

    operator fun invoke(query: String): Flow<Result<List<Country>>>
}
