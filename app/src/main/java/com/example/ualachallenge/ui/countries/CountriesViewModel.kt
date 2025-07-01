package com.example.ualachallenge.ui.countries

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
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
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
    private var jobGetCountries: Job? = null

    private val _countriesUiState = MutableStateFlow(CountriesUiState())
    val countriesUiState = _countriesUiState.asStateFlow()

    fun activeSearch(isActiveSearch: Boolean) {
        this.isActiveSearch = isActiveSearch
    }

    fun initGetCountries() {
        if (countriesUiState.value.countries != null) return
        getCountries()
    }

    fun searchMovies(query: String) {
        if (this.query == query) return
        this.query = query
        getCountries()
    }

    fun retryGetCountries() {
        if (countriesUiState.value.isLoading) return
        getCountries()
    }

    private fun getCountries() {
        jobGetCountries?.cancel()
        jobGetCountries = viewModelScope.launch(coroutinesDispatchers.io) {
            updateCountriesUiState(isLoading = true)
            getCountriesUseCase(query).collect {
                getCountriesSuccess(it)
                getCountriesError(it)
            }
        }
    }

    private fun getCountriesSuccess(result: Result<List<Country>>) = result.onSuccess {
        updateCountriesUiState(countries = it)
    }

    private fun getCountriesError(result: Result<List<Country>>) = result.onFailure {
        updateCountriesUiState(countries = null, error = it)
        it.printStackTrace()
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

    fun updateFavorite(id: Int, isFavorite: Boolean) {
        viewModelScope.launch(coroutinesDispatchers.io) {
            updateFavoriteUseCase(id, isFavorite)
        }
    }
}
