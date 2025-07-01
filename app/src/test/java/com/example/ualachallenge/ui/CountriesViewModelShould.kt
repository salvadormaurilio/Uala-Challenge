package com.example.ualachallenge.ui

import com.example.ualachallenge.core.TestDispatcherRule
import com.example.ualachallenge.core.assertThatEquals
import com.example.ualachallenge.core.assertThatIsInstanceOf
import com.example.ualachallenge.data.datasource.exception.DataException
import com.example.ualachallenge.domain.GetCountriesUseCase
import com.example.ualachallenge.domain.UpdateFavoriteUseCase
import com.example.ualachallenge.fakedata.ANY_ID
import com.example.ualachallenge.fakedata.ANY_QUERY
import com.example.ualachallenge.fakedata.givenCountriesFakeData
import com.example.ualachallenge.ui.countries.CountriesViewModel
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.Mockito.verify
import org.mockito.kotlin.whenever

class CountriesViewModelShould {

    @get:Rule
    var testDispatcherRule = TestDispatcherRule()

    private val getCountriesUseCase = mock<GetCountriesUseCase>()
    private val updateFavoriteUseCase = mock<UpdateFavoriteUseCase>()
    private lateinit var countriesViewModel: CountriesViewModel

    @Before
    fun setup() {
        countriesViewModel = CountriesViewModel(getCountriesUseCase, updateFavoriteUseCase, testDispatcherRule.coroutinesDispatchers)
    }

    @Test
    fun `Get Countries data when initGetCountries is called and getCountries is success`() = runTest {
        val countries = givenCountriesFakeData()
        whenever(getCountriesUseCase()).thenReturn(flowOf(Result.success(countries)))

        countriesViewModel.initGetCountries()

        val result = countriesViewModel.countriesUiState.firstOrNull()

        verify(getCountriesUseCase)()
        assertThatEquals(result?.countries, countries)
    }

    @Test
    fun `Get Countries data when searchCountries is called and getCountries is success`() = runTest {
        val countries = givenCountriesFakeData()
        whenever(getCountriesUseCase(ANY_QUERY)).thenReturn(flowOf(Result.success(countries)))

        countriesViewModel.searchCountries(ANY_QUERY)

        val result = countriesViewModel.countriesUiState.firstOrNull()

        verify(getCountriesUseCase)(ANY_QUERY)
        assertThatEquals(result?.countries, countries)
    }

    @Test
    fun `Get CountriesException data when searchCountries is called and getCountries is failure`() = runTest {
        whenever(getCountriesUseCase(ANY_QUERY)).thenReturn(flowOf(Result.failure(DataException.CountriesException())))

        countriesViewModel.searchCountries(ANY_QUERY)

        val result = countriesViewModel.countriesUiState.firstOrNull()

        verify(getCountriesUseCase)(ANY_QUERY)
        assertThatIsInstanceOf<DataException.CountriesException>(result?.error)
    }

    @Test
    fun `Get CountriesException data when initGetCountries is called and getCountries is failure`() = runTest {
        whenever(getCountriesUseCase()).thenReturn(flowOf(Result.failure(DataException.CountriesException())))

        countriesViewModel.initGetCountries()

        val result = countriesViewModel.countriesUiState.firstOrNull()

        verify(getCountriesUseCase)()
        assertThatIsInstanceOf<DataException.CountriesException>(result?.error)
    }

    @Test
    fun `Get Countries data when retryGetCountries is called and getCountries is success`() = runTest {
        val countries = givenCountriesFakeData()
        whenever(getCountriesUseCase()).thenReturn(flowOf(Result.success(countries)))

        countriesViewModel.retryGetCountries()

        val result = countriesViewModel.countriesUiState.firstOrNull()

        verify(getCountriesUseCase)()
        assertThatEquals(result?.countries, countries)
    }

    @Test
    fun `Get CountriesException data when retryGetCountries is called and getCountries is failure`() = runTest {
        whenever(getCountriesUseCase()).thenReturn(flowOf(Result.failure(DataException.CountriesException())))

        countriesViewModel.retryGetCountries()
        val result = countriesViewModel.countriesUiState.firstOrNull()

        verify(getCountriesUseCase)()
        assertThatIsInstanceOf<DataException.CountriesException>(result?.error)
    }

    @Test
    fun `Call updateFavorite when updateFavorite is called`() = runTest {
        countriesViewModel.updateFavorite(id = ANY_ID, isFavorite = true)

        verify(updateFavoriteUseCase)(id = ANY_ID, isFavorite = true)
    }
}
