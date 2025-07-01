package com.example.ualachallenge.data.datasource.local

import com.example.ualachallenge.data.datasource.exception.DataException
import com.example.ualachallenge.data.datasource.local.room.CountriesDao
import com.example.ualachallenge.data.datasource.local.room.CountryEntity
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class CountriesLocalDataSourceImpl @Inject constructor(private val countriesDao: CountriesDao) : CountriesLocalDataSource {

    override suspend fun insertCountries(countries: List<CountryEntity>) = countriesDao.insertAll(countries)

    override fun getCountries() = flow {
        try {
            val countriesEntity = countriesDao.getAll()
            emit(Result.success(countriesEntity))
        } catch (exception: Exception) {
            exception.printStackTrace()
            emit(Result.failure(DataException.CountriesException()))
        }
    }
}
