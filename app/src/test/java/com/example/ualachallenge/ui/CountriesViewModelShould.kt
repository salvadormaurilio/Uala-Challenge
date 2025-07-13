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
import com.example.ualachallenge.fakedata.givenCountriesWithFavoritesFakeData
import com.example.ualachallenge.fakedata.givenCountryDetailRouteFakeData
import com.example.ualachallenge.fakedata.givenCountryFakeData
import com.example.ualachallenge.fakedata.givenCountryMapRouteFakeData
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
    fun `Get CountriesException data when initGetCountries is called and getCountries is failure`() = runTest {
        whenever(getCountriesUseCase()).thenReturn(flowOf(Result.failure(DataException.CountriesException())))

        countriesViewModel.initGetCountries()

        val result = countriesViewModel.countriesUiState.firstOrNull()

        verify(getCountriesUseCase)()
        assertThatIsInstanceOf<DataException.CountriesException>(result?.error)
    }

    @Test
    fun `Get Countries data when searchCountries is called and getCountries is success`() = runTest {
        val countries = givenCountriesFakeData()
        whenever(getCountriesUseCase(query = ANY_QUERY)).thenReturn(flowOf(Result.success(countries)))

        countriesViewModel.searchCountries(ANY_QUERY)

        val result = countriesViewModel.countriesUiState.firstOrNull()

        verify(getCountriesUseCase)(query = ANY_QUERY)
        assertThatEquals(result?.countries, countries)
    }

    @Test
    fun `Get CountriesException data when searchCountries is called and getCountries is failure`() = runTest {
        whenever(getCountriesUseCase(query = ANY_QUERY)).thenReturn(flowOf(Result.failure(DataException.CountriesException())))

        countriesViewModel.searchCountries(ANY_QUERY)

        val result = countriesViewModel.countriesUiState.firstOrNull()

        verify(getCountriesUseCase)(query = ANY_QUERY)
        assertThatIsInstanceOf<DataException.CountriesException>(result?.error)
    }

    @Test
    fun `Get Countries data when filterFavorites is called and getCountries is success`() = runTest {
        val countries = givenCountriesFakeData()
        whenever(getCountriesUseCase(filterFavorites = true)).thenReturn(flowOf(Result.success(countries)))

        countriesViewModel.filterFavorites(true)

        val result = countriesViewModel.countriesUiState.firstOrNull()

        verify(getCountriesUseCase)(filterFavorites = true)
        assertThatEquals(result?.countries, countries)
    }

    @Test
    fun `Get CountriesException data when filterFavorites is called and getCountries is failure`() = runTest {
        whenever(getCountriesUseCase(filterFavorites = true)).thenReturn(flowOf(Result.failure(DataException.CountriesException())))

        countriesViewModel.filterFavorites(true)
        val result = countriesViewModel.countriesUiState.firstOrNull()

        verify(getCountriesUseCase)(filterFavorites = true)
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
    fun `Call updateFavorite and update Countries when updateFavorite is called`() = runTest {
        val countries = givenCountriesFakeData()
        val countriesUpdatedFavorites = givenCountriesWithFavoritesFakeData()
        whenever(getCountriesUseCase()).thenReturn(flowOf(Result.success(countries)))

        countriesViewModel.initGetCountries()
        countriesViewModel.updateFavorite(id = ANY_ID, isFavorite = true)

        val result = countriesViewModel.countriesUiState.firstOrNull()

        verify(updateFavoriteUseCase)(id = ANY_ID, isFavorite = true)
        assertThatEquals(result?.countries, countriesUpdatedFavorites)
    }

    @Test
    fun `Navigate to Country Map Route when navigateToCountryMap is called`() = runTest {
        val country = givenCountryFakeData()
        val countryMapRoute = givenCountryMapRouteFakeData()

        countriesViewModel.navigateToCountryMap(country)

        val result = countriesViewModel.navigateToCountryRoutes.firstOrNull()

        assertThatEquals(result, countryMapRoute)
    }

    @Test
    fun `Navigate to Country Detail Route when navigateToCountryMap is called`() = runTest {
        val country = givenCountryFakeData()
        val countryDetailRoute = givenCountryDetailRouteFakeData()

        countriesViewModel.navigateToCountryDetail(country)

        val result = countriesViewModel.navigateToCountryRoutes.firstOrNull()

        assertThatEquals(result, countryDetailRoute)
    }
}

