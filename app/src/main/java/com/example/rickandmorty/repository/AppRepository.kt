package com.example.rickandmorty.repository

import com.example.rickandmorty.db.AppDatabase
import com.example.rickandmorty.db.entities.Favorite
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class AppRepository : KoinComponent {
    private val appDatabase: AppDatabase by inject()

    suspend fun getFavoritesDB() =
        appDatabase.favoriteDao.getFavoriteCharacters()
    suspend fun addFavoriteDB(favorite: Favorite) =
        appDatabase.favoriteDao.addFavoriteCharacter(favorite = favorite)

    suspend fun removeFavoriteDB(characterId: Int) =
        appDatabase.favoriteDao.removeFavoriteCharacter(characterId = characterId)

    suspend fun getCharacters(page: Int = 1) =
        CharactersRepository().getCharacters(page = page)

    suspend fun getCharacterById(characterId: Int) =
        CharactersRepository().getCharacterById(characterId = characterId)

    suspend fun searchCharacters(characterName: String, page: Int) =
        CharactersRepository().searchCharacters(characterName = characterName, page = page)
}