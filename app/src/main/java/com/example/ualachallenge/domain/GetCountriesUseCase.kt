package com.example.ualachallenge.domain

import com.example.ualachallenge.core.extensions.empty
import com.example.ualachallenge.domain.model.Country
import kotlinx.coroutines.flow.Flow

interface GetCountriesUseCase {

    operator fun invoke(query: String = String.empty()): Flow<Result<List<Country>>>
}
