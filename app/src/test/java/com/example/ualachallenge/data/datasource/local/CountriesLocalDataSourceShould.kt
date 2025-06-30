package com.example.ualachallenge.data.datasource.local

import com.example.ualachallenge.core.assertThatEquals
import com.example.ualachallenge.core.assertThatIsInstanceOf
import com.example.ualachallenge.data.datasource.exception.DataException
import com.example.ualachallenge.data.datasource.local.room.CountriesDao
import com.example.ualachallenge.fakedata.givenCountriesEntityFakeData
import kotlinx.coroutines.flow.lastOrNull
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever

class CountriesLocalDataSourceShould {

    private val userDao = mock<CountriesDao>()

    private lateinit var countriesLocalDataSource: CountriesLocalDataSource

    @Before
    fun setup() {
        countriesLocalDataSource = CountriesLocalDataSourceImpl(userDao)
    }

    @Test
    fun `Call insertAll when insertCountries is called`() = runTest {
        val countriesEntity = givenCountriesEntityFakeData()
        countriesLocalDataSource.insertCountries(countriesEntity)

        verify(userDao).insertAll(countriesEntity)
    }

    @Test
    fun `Get CountriesEntity when getAll is success`() = runTest {
        val countriesEntity = givenCountriesEntityFakeData()
        whenever(userDao.getAll()).thenReturn(countriesEntity)

        val result = countriesLocalDataSource.getCountries().lastOrNull()

        verify(userDao).getAll()
        assertThatEquals(result?.getOrNull(), countriesEntity)
    }

    @Test
    fun `Get CountriesException when getAll is failure`() = runTest {
        whenever(userDao.getAll()).thenThrow(RuntimeException())

        val result = countriesLocalDataSource.getCountries().lastOrNull()

        verify(userDao).getAll()
        assertThatIsInstanceOf<DataException.CountriesException>(result?.exceptionOrNull())
    }
}
