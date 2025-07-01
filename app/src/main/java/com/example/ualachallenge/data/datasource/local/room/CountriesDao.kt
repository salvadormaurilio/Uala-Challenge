package com.example.ualachallenge.data.datasource.local.room

import androidx.annotation.VisibleForTesting
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction

@Dao
interface CountriesDao {

    @Transaction
    suspend fun insertAll(countries: List<CountryEntity>) {
        delete()
        insert(countries)
    }

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(countries: List<CountryEntity>)

    @Query("SELECT * FROM $TABLE_COUNTRY")
    suspend fun getAll(): List<CountryEntity>

    @VisibleForTesting
    @Query("DELETE FROM $TABLE_COUNTRY")
    suspend fun delete(): Int

    @Query("UPDATE $TABLE_COUNTRY SET $IS_FAVORITE = :isFavorite WHERE $ID = :id")
    suspend fun updateFavorite(id: Int, isFavorite: Boolean)
}
