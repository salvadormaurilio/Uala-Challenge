package com.example.ualachallenge.ui.countries

import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ualachallenge.core.coroutines.CoroutinesDispatchers
import com.example.ualachallenge.core.extensions.empty
import com.example.ualachallenge.domain.GetCountriesUseCase
import com.example.ualachallenge.domain.UpdateFavoriteUseCase
import com.example.ualachallenge.domain.model.Country
import com.example.ualachallenge.domain.model.filterCountries
import com.example.ualachallenge.domain.model.toCountryDetailRoute
import com.example.ualachallenge.domain.model.toCountryMapRoute
import com.example.ualachallenge.domain.model.updateFavorite
import com.example.ualachallenge.ui.home.CountryRoutes
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CountriesViewModel @Inject constructor(
    private val getCountriesUseCase: GetCountriesUseCase,
    private val updateFavoriteUseCase: UpdateFavoriteUseCase,
    private val coroutinesDispatchers: CoroutinesDispatchers
) : ViewModel() {

    var isActiveSearch by mutableStateOf(false)
        private set
    var query by mutableStateOf(String.empty())
        private set
    var filterFavorites by mutableStateOf(false)
        private set
    val listState = LazyListState()

    private var jobGetCountries: Job? = null

    private val _countriesUiState = MutableStateFlow(CountriesUiState())
    val countriesUiState = _countriesUiState.asStateFlow()

    private val _navigateToCountryRoutes = Channel<CountryRoutes>()
    val navigateToCountryRoutes = _navigateToCountryRoutes.receiveAsFlow()

    fun activeSearch(isActiveSearch: Boolean) {
        this.isActiveSearch = isActiveSearch
    }

    fun initGetCountries() {
        if (countriesUiState.value.countries != null) return
        getCountries()
    }

    fun searchCountries(query: String) {
        if (this.query == query) return
        this.query = query
        getCountries()
    }

    fun filterFavorites(filterFavorites: Boolean) {
        if (this.filterFavorites == filterFavorites) return
        this.filterFavorites = filterFavorites
        getCountries()
    }

    fun retryGetCountries() {
        if (countriesUiState.value.isLoading) return
        getCountries()
    }

    private fun getCountries() {
        jobGetCountries?.cancel()
        jobGetCountries = viewModelScope.launch(coroutinesDispatchers.io) {
            delay(DELAY_QUERY)
            updateCountriesUiState(isLoading = true)
            getCountriesUseCase(query = query.trim(), filterFavorites = filterFavorites).collect {
                getCountriesSuccess(it)
                getCountriesError(it)
            }
        }
    }

    private fun getCountriesSuccess(result: Result<List<Country>>) = result.onSuccess {
        viewModelScope.launch { listState.scrollToItem(0) }
        updateCountriesUiState(countries = it)
    }

    private fun getCountriesError(result: Result<List<Country>>) = result.onFailure {
        updateCountriesUiState(countries = null, error = it)
        it.printStackTrace()
    }

    fun updateFavorite(id: Int, isFavorite: Boolean) {
        viewModelScope.launch(coroutinesDispatchers.io) {
            updateFavoriteUseCase(id, isFavorite)
            val newCountries = countriesUiState.value.countries?.updateFavorite(id, isFavorite)?.filterCountries(query, filterFavorites)
            updateCountriesUiState(countries = newCountries)

        }
    }

    private fun updateCountriesUiState(
        isLoading: Boolean = false,
        countries: List<Country>? = countriesUiState.value.countries,
        error: Throwable? = null
    ) {
        _countriesUiState.update {
            it.copy(
                isLoading = isLoading,
                countries = countries,
                error = error
            )
        }
    }

    fun navigateToCountryMap(country: Country) {
        viewModelScope.launch {
            _navigateToCountryRoutes.send(country.toCountryMapRoute())
        }
    }

    fun navigateToCountryDetail(country: Country) {
        viewModelScope.launch {
            _navigateToCountryRoutes.send(country.toCountryDetailRoute())
        }
    }

    companion object {
        const val DELAY_QUERY = 300L
    }
}
