package com.example.ualachallenge.data.local

import com.example.ualachallenge.core.RoomRule
import com.example.ualachallenge.core.assertThatEquals
import com.example.ualachallenge.data.datasource.local.room.CountriesDao
import com.example.ualachallenge.data.datasource.local.room.CountriesRoomDatabase
import com.example.ualachallenge.fakedata.givenCountriesEntityFakeData
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

        countriesDao.insert(countriesEntity)
        val countriesEntityResult = countriesDao.getAll()

        assertThatEquals(countriesEntityResult, countriesEntity)
    }

    @Test
    fun replaceCountriesEntityInDatabaseWhenInsertIsCalledMultipleTimes() = runTest {
        val countriesEntity = givenCountriesEntityFakeData()

        countriesDao.insert(countriesEntity)
        countriesDao.insert(countriesEntity)
        val countriesEntityResult = countriesDao.getAll()

        assertThatEquals(countriesEntityResult, countriesEntity)
    }
}
