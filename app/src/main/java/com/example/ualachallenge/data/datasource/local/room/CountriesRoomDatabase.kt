package com.example.ualachallenge.data.datasource.local.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(
    entities = [CountryEntity::class],
    version = DATABASE_COUNTRY_VERSION,
    exportSchema = false
)
abstract class CountriesRoomDatabase : RoomDatabase() {

    abstract fun countriesDao(): CountriesDao

    companion object {

        @Volatile
        private var instance: CountriesRoomDatabase? = null

        fun getInstance(context: Context): CountriesRoomDatabase =
            instance ?: synchronized(this) {
                build(context).also { instance = it }
            }

        private fun build(context: Context) =
            Room.databaseBuilder(context, CountriesRoomDatabase::class.java, DATABASE_COUNTRY_NAME)
                .fallbackToDestructiveMigration(false)
                .build()
    }
}
