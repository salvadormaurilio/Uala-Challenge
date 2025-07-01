package com.example.ualachallenge.domain

import com.example.ualachallenge.data.CountriesRepository
import com.example.ualachallenge.domain.model.filterCountries
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetCountriesUseCaseImpl @Inject constructor(private val countriesRepository: CountriesRepository) : GetCountriesUseCase {

    override operator fun invoke(query: String) = countriesRepository.getCountries()
        .map { it.filterCountries(query) }
}
