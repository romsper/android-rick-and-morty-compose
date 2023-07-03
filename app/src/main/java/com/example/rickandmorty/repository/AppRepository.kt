package com.example.rickandmorty.repository

import com.example.rickandmorty.db.dao.IFavoriteDao
import com.example.rickandmorty.db.entities.Favorite
import javax.inject.Inject

class AppRepository @Inject constructor(
    private val favoriteDao: IFavoriteDao,
//    private val userDao: IUserDao
) {

    suspend fun getFavoritesDB() =
        favoriteDao.getFavoriteCharacters()
    suspend fun addFavoriteDB(favorite: Favorite) =
        favoriteDao.addFavoriteCharacter(favorite = favorite)

    suspend fun removeFavoriteDB(characterId: Int) =
        favoriteDao.removeFavoriteCharacter(characterId = characterId)

    suspend fun getCharacters(page: Int = 1) =
        CharactersRepository().getCharacters(page = page)

    suspend fun getCharacterById(characterId: Int) =
        CharactersRepository().getCharacterById(characterId = characterId)

    suspend fun searchCharacters(characterName: String, page: Int) =
        CharactersRepository().searchCharacters(characterName = characterName, page = page)
}