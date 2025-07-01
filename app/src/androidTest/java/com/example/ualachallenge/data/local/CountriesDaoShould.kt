package com.example.ualachallenge.data.local

import com.example.ualachallenge.core.RoomRule
import com.example.ualachallenge.core.assertThatEquals
import com.example.ualachallenge.data.datasource.local.room.CountriesDao
import com.example.ualachallenge.data.datasource.local.room.CountriesRoomDatabase
import com.example.ualachallenge.fakedata.ANY_ID
import com.example.ualachallenge.fakedata.givenCountriesEntityFakeData
import com.example.ualachallenge.fakedata.givenCountryEntityUpdatedFakeData
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class CountriesDaoShould {

    @get:Rule
    val userRoomDatabase = RoomRule.build<CountriesRoomDatabase>()

    private lateinit var countriesDao: CountriesDao

    @Before
    fun setUp() {
        countriesDao = userRoomDatabase.database().countriesDao()
    }

    @Test
    fun insertCountriesEntityInDatabase() = runTest {
        val countriesEntity = givenCountriesEntityFakeData()

        countriesDao.insertAll(countriesEntity)
        val countriesEntityResult = countriesDao.getAll()

        assertThatEquals(countriesEntityResult, countriesEntity)
    }

    @Test
    fun replaceCountriesEntityInDatabaseWhenInsertIsCalledMultipleTimes() = runTest {
        val countriesEntity = givenCountriesEntityFakeData()

        countriesDao.insertAll(countriesEntity)
        countriesDao.insertAll(countriesEntity)
        val countriesEntityResult = countriesDao.getAll()

        assertThatEquals(countriesEntityResult, countriesEntity)
    }

    @Test
    fun updateEntityInDatabaseWhenUpdateFavoriteIsCalled() = runTest {
        val countriesEntity = givenCountriesEntityFakeData()
        val countriesEntityUpdated = givenCountryEntityUpdatedFakeData()

        countriesDao.insertAll(countriesEntity)
        countriesDao.updateFavorite(id = ANY_ID, isFavorite = true)
        val countriesEntityResult = countriesDao.getAll().find { it.id == ANY_ID }

        assertThatEquals(countriesEntityResult, countriesEntityUpdated)
    }
}
