package com.example.ualachallenge.data

import com.example.ualachallenge.data.datasource.local.CountriesLocalDataSource
import com.example.ualachallenge.data.datasource.local.room.toCountries
import com.example.ualachallenge.data.datasource.remote.CountriesRemoteDataSource
import com.example.ualachallenge.data.datasource.remote.retrofit.toCountries
import com.example.ualachallenge.domain.model.Country
import com.example.ualachallenge.domain.model.getCountries
import com.example.ualachallenge.domain.model.isValidCountries
import com.example.ualachallenge.domain.model.toCountriesEntity
import com.example.ualachallenge.domain.model.updateFavorite
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

class CountriesRepositoryImpl @Inject constructor(
    private val countriesRemoteDataSource: CountriesRemoteDataSource,
    private val countriesLocalDataSource: CountriesLocalDataSource
) : CountriesRepository {

    internal var countries = emptyList<Country>()

    override fun getCountries() = if (countries.isEmpty()) getOrFetchCountries() else flowOf(Result.success(countries))

    @OptIn(ExperimentalCoroutinesApi::class)
    private fun getOrFetchCountries() = countriesLocalDataSource.getCountries()
        .map { countriesEntity -> countriesEntity.toCountries() }
        .flatMapLatest { countriesEntity -> emitCountriesOrFetchCountries(countriesEntity) }

    private fun emitCountriesOrFetchCountries(countriesResult: Result<List<Country>>) =
        if (countriesResult.isValidCountries()) {
            countries = countriesResult.getCountries()
            flowOf(countriesResult)
        } else {
            fetchAndStoreCountries()
        }

    private fun fetchAndStoreCountries() = countriesRemoteDataSource.fetchCountries()
        .map { countriesResponse -> countriesResponse.toCountries() }
        .onEach { countriesResult ->
            if (countriesResult.isValidCountries()) {
                countries = countriesResult.getCountries()
                countriesLocalDataSource.insertCountries(countries.toCountriesEntity())
            }
        }

    override suspend fun updateFavorite(id: Int, isFavorite: Boolean) {
        countries.updateFavorite(id, isFavorite)
        return countriesLocalDataSource.updateFavorite(id, isFavorite)
    }
}

