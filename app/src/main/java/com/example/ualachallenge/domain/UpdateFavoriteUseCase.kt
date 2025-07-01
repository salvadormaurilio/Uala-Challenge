package com.example.ualachallenge.domain

interface UpdateFavoriteUseCase {

    suspend operator fun invoke(id: Int, isFavorite: Boolean)
}
