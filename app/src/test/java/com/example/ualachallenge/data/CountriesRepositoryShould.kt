package com.example.ualachallenge.data

import com.example.ualachallenge.core.assertThatEquals
import com.example.ualachallenge.core.assertThatIsInstanceOf
import com.example.ualachallenge.data.datasource.exception.DataException
import com.example.ualachallenge.data.datasource.local.CountriesLocalDataSource
import com.example.ualachallenge.data.datasource.local.room.CountryEntity
import com.example.ualachallenge.data.datasource.remote.CountriesRemoteDataSource
import com.example.ualachallenge.data.datasource.remote.retrofit.CountryResponse
import com.example.ualachallenge.fakedata.ANY_ID
import com.example.ualachallenge.fakedata.givenCountriesEntityFakeData
import com.example.ualachallenge.fakedata.givenCountriesFakeData
import com.example.ualachallenge.fakedata.givenCountriesResponseFakeData
import com.example.ualachallenge.fakedata.givenCountriesWithFavoritesFakeData
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.lastOrNull
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.kotlin.any
import org.mockito.kotlin.never
import org.mockito.kotlin.times
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever

class CountriesRepositoryShould {

    private val countriesRemoteDataSource = mock<CountriesRemoteDataSource>()
    private val countriesLocalDataSource = mock<CountriesLocalDataSource>()
    private lateinit var countriesRepository: CountriesRepository

    @Before
    fun setup() {
        countriesRepository = CountriesRepositoryImpl(countriesRemoteDataSource, countriesLocalDataSource)
    }

    @Test
    fun `Get Countries data when current Countries is empty and getCountries from local is success`() = runTest {
        val countriesEntity = givenCountriesEntityFakeData()
        val countries = givenCountriesFakeData()
        val resultCountriesEntitySuccess = Result.success(countriesEntity)
        whenever(countriesLocalDataSource.getCountries()).thenReturn(flowOf(resultCountriesEntitySuccess))

        val result = countriesRepository.getCountries().lastOrNull()

        verify(countriesLocalDataSource).getCountries()
        verify(countriesRemoteDataSource, never()).fetchCountries()
        verify(countriesLocalDataSource, never()).insertCountries(any())
        assertThatEquals(result?.getOrNull(), countries)
    }

    @Test
    fun `Get Countries data when current Countries is empty and getCountries from local is empty and fetchCountries from remote is success`() =
        runTest {
            val countriesResponse = givenCountriesResponseFakeData()
            val countries = givenCountriesFakeData()
            val countriesEntity = givenCountriesEntityFakeData()
            val resultCountriesEntityEmptySuccess: Result<List<CountryEntity>> = Result.success(emptyList())
            val resultCountriesResponseSuccess = Result.success(countriesResponse)
            whenever(countriesLocalDataSource.getCountries()).thenReturn(flowOf(resultCountriesEntityEmptySuccess))
            whenever(countriesRemoteDataSource.fetchCountries()).thenReturn(flowOf(resultCountriesResponseSuccess))

            val result = countriesRepository.getCountries().lastOrNull()

            verify(countriesLocalDataSource).getCountries()
            verify(countriesRemoteDataSource).fetchCountries()
            verify(countriesLocalDataSource).insertCountries(countriesEntity)
            assertThatEquals(result?.getOrNull(), countries)
        }

    @Test
    fun `Get Countries data when current Countries is empty and getCountries from local is failure and fetchCountries from remote is success`() =
        runTest {
            val countriesResponse = givenCountriesResponseFakeData()
            val countries = givenCountriesFakeData()
            val countriesEntity = givenCountriesEntityFakeData()
            val resultCountriesEntityFailure: Result<List<CountryEntity>> =
                Result.failure(DataException.CountriesException())
            val resultCountriesResponseSuccess = Result.success(countriesResponse)
            whenever(countriesLocalDataSource.getCountries()).thenReturn(flowOf(resultCountriesEntityFailure))
            whenever(countriesRemoteDataSource.fetchCountries()).thenReturn(flowOf(resultCountriesResponseSuccess))

            val result = countriesRepository.getCountries().lastOrNull()

            verify(countriesLocalDataSource).getCountries()
            verify(countriesRemoteDataSource).fetchCountries()
            verify(countriesLocalDataSource).insertCountries(countriesEntity)
            assertThatEquals(result?.getOrNull(), countries)
        }

    @Test
    fun `Get CountriesException when current Countries is empty and getCountries from local is failure and fetchCountries from remote is failure`() =
        runTest {
            val resultCountriesEntityFailure: Result<List<CountryEntity>> =
                Result.failure(DataException.CountriesException())
            val resultCountriesResponseFailure: Result<List<CountryResponse>> =
                Result.failure(DataException.CountriesException())
            whenever(countriesLocalDataSource.getCountries()).thenReturn(flowOf(resultCountriesEntityFailure))
            whenever(countriesRemoteDataSource.fetchCountries()).thenReturn(flowOf(resultCountriesResponseFailure))

            val result = countriesRepository.getCountries().lastOrNull()

            verify(countriesLocalDataSource).getCountries()
            verify(countriesRemoteDataSource).fetchCountries()
            verify(countriesLocalDataSource, never()).insertCountries(any())
            assertThatIsInstanceOf<DataException.CountriesException>(result?.exceptionOrNull())
        }

    @Test
    fun `Get Countries data when current Countries is not empty when getCountries is called previously`() =
        runTest {
            val countriesEntity = givenCountriesEntityFakeData()
            val countries = givenCountriesFakeData()
            val resultCountriesEntitySuccess = Result.success(countriesEntity)
            whenever(countriesLocalDataSource.getCountries()).thenReturn(flowOf(resultCountriesEntitySuccess))

            countriesRepository.getCountries().lastOrNull()
            val result = countriesRepository.getCountries().lastOrNull()

            verify(countriesLocalDataSource, times(1)).getCountries()
            verify(countriesRemoteDataSource, never()).fetchCountries()
            verify(countriesLocalDataSource, never()).insertCountries(any())
            assertThatEquals(result?.getOrNull(), countries)
        }

    @Test
    fun `Call updateFavorite and update Countries when updateFavorite is called`() = runTest {
        val countriesEntity = givenCountriesEntityFakeData()
        val countries = givenCountriesWithFavoritesFakeData()
        val resultCountriesEntitySuccess = Result.success(countriesEntity)
        whenever(countriesLocalDataSource.getCountries()).thenReturn(flowOf(resultCountriesEntitySuccess))

        countriesRepository.getCountries().lastOrNull()
        countriesRepository.updateFavorite(id = ANY_ID, isFavorite = true)
        val result = countriesRepository.getCountries().lastOrNull()

        verify(countriesLocalDataSource).updateFavorite(id = ANY_ID, isFavorite = true)
        assertThatEquals(result?.getOrNull(), countries)

    }
}
