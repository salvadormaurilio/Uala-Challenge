package com.example.ualachallenge.data.datasource.local.room

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.ualachallenge.domain.model.Country

@Entity(tableName = TABLE_COUNTRY)
data class CountryEntity(
    @PrimaryKey val id: Int,
    val name: String,
    val country: String,
    val longitude: Double,
    val latitude: Double,
    val isFavorite: Boolean = false,
)

fun Result<List<CountryEntity>>.toCountries() = map { it.toCountries() }

private fun List<CountryEntity>.toCountries() = map { it.toCountry() }

private fun CountryEntity.toCountry() = Country(
    id = id,
    name = name,
    country = country,
    longitude = longitude,
    latitude = latitude,
    isFavorite = isFavorite
)

