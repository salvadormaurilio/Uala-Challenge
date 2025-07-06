package com.example.ualachallenge.domain

import com.example.ualachallenge.core.assertThatEquals
import com.example.ualachallenge.core.assertThatIsInstanceOf
import com.example.ualachallenge.data.CountriesRepository
import com.example.ualachallenge.data.datasource.exception.DataException
import com.example.ualachallenge.domain.model.Country
import com.example.ualachallenge.fakedata.ANY_QUERY
import com.example.ualachallenge.fakedata.givenCountriesFakeData
import com.example.ualachallenge.fakedata.givenCountriesFilteredByQueryFakeData
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.lastOrNull
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever

class GetCountriesUseCaseShould {

    private val countriesRepository = mock<CountriesRepository>()
    private lateinit var getCountriesUseCase: GetCountriesUseCase

    @Before
    fun setup() {
        getCountriesUseCase = GetCountriesUseCaseImpl(countriesRepository)
    }

    @Test
    fun `Get Countries data when query is empty and getCountries is success`() = runTest {
        val countries = givenCountriesFakeData()
        val resultSuccess = Result.success(countries)
        whenever(countriesRepository.getCountries()).thenReturn(flowOf(resultSuccess))

        val result = getCountriesUseCase().lastOrNull()

        verify(countriesRepository).getCountries()
        assertThatEquals(result?.getOrNull(), countries)
    }

    @Test
    fun `Get CountriesException data when query has some value is success`() = runTest {
        val countries = givenCountriesFilteredByQueryFakeData()
        val resultSuccess = Result.success(countries)
        whenever(countriesRepository.getCountries()).thenReturn(flowOf(resultSuccess))

        val result = getCountriesUseCase(ANY_QUERY).lastOrNull()

        verify(countriesRepository).getCountries()
        assertThatEquals(result?.getOrNull(), countries)
    }

    @Test
    fun `Get CountriesException data when getCountries is failure`() = runTest {
        val resultFailure: Result<List<Country>> = Result.failure(DataException.CountriesException())
        whenever(countriesRepository.getCountries()).thenReturn(flowOf(resultFailure))

        val result = getCountriesUseCase().lastOrNull()

        verify(countriesRepository).getCountries()
        assertThatIsInstanceOf<DataException.CountriesException>(result?.exceptionOrNull())
    }
}
