package com.example.ualachallenge.ui.countries

import com.example.ualachallenge.domain.model.Country

data class CountriesUiState(
    val isLoading: Boolean = false,
    val countries: List<Country>? = null,
    val error: Throwable? = null
)
