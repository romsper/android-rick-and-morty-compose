package com.example.rickandmorty.repository

import com.example.rickandmorty.db.AppDatabase
import com.example.rickandmorty.db.entities.Favorite
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class DataRepository : KoinComponent {
    private val appDatabase: AppDatabase by inject()

    suspend fun getFavoritesDB() = appDatabase.favoriteDao.getFavoriteCharacters()
    suspend fun addFavoriteDB(favorite: Favorite) = appDatabase.favoriteDao.addFavoriteCharacter(favorite = favorite)
    suspend fun removeFavoriteDB(characterId: Int) = appDatabase.favoriteDao.removeFavoriteCharacter(characterId = characterId)
}