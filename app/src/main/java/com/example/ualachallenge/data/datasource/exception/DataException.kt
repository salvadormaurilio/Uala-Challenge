package com.example.ualachallenge.data.datasource.exception

sealed class DataException(message: String) : Exception(message) {
    data class CountriesException(override val message: String = "Some error happened with the get Countries Data") : Exception(message)
}
