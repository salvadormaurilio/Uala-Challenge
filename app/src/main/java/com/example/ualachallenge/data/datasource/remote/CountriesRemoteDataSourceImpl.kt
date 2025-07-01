package com.example.ualachallenge.data.datasource.remote

import com.example.ualachallenge.data.datasource.exception.DataException
import com.example.ualachallenge.data.datasource.remote.retrofit.CountriesServiceRetrofit
import com.example.ualachallenge.data.datasource.remote.retrofit.CountryResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class CountriesRemoteDataSourceImpl @Inject constructor(
    private val countriesServiceRetrofit: CountriesServiceRetrofit
) : CountriesRemoteDataSource {

    override fun fetchCountries(): Flow<Result<List<CountryResponse>>> = flow {
        try {
            val countriesResponse = countriesServiceRetrofit.fetchCountries()
            emit(Result.success(countriesResponse))
        } catch (exception: Exception) {
            exception.printStackTrace()
            emit(Result.failure(DataException.CountriesException()))
        }
    }
}
