package com.example.ualachallenge.domain

import com.example.ualachallenge.data.CountriesRepository
import javax.inject.Inject

class UpdateFavoriteUseCaseImpl @Inject constructor(private val countriesRepository: CountriesRepository) : UpdateFavoriteUseCase {

    override suspend operator fun invoke(id: Int, isFavorite: Boolean) = countriesRepository.updateFavorite(id, isFavorite)
}
