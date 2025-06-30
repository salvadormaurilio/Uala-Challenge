package com.example.ualachallenge.data.datasource.local.room

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = TABLE_COUNTRY)
data class CountryEntity(
    @PrimaryKey val id: Int,
    val name: String,
    val country: String,
    val longitude: Double,
    val latitude: Double,
)
