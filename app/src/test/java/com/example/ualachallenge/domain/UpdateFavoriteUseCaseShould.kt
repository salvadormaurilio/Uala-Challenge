package com.example.ualachallenge.domain

import com.example.ualachallenge.data.CountriesRepository
import com.example.ualachallenge.fakedata.ANY_ID
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.kotlin.verify

class UpdateFavoriteUseCaseShould {

    private val countriesRepository = mock<CountriesRepository>()
    private lateinit var updateFavoriteUseCase: UpdateFavoriteUseCase

    @Before
    fun setup() {
        updateFavoriteUseCase = UpdateFavoriteUseCaseImpl(countriesRepository)
    }

    @Test
    fun `Call updateFavorite when updateFavorite is called`() = runTest {
        updateFavoriteUseCase(id = ANY_ID, isFavorite = true)

        verify(countriesRepository).updateFavorite(id = ANY_ID, isFavorite = true)
    }
}
