package com.example.rickandmorty.repository

import com.example.rickandmorty.network.Retrofit

open class CharactersRepository {

    suspend fun getCharacters(page: Int) = Retrofit.rickAndMortyApi.getCharacters(page = page)
    suspend fun getCharacterById(characterId: Int) = Retrofit.rickAndMortyApi.getCharacterById(characterId = characterId)
    suspend fun searchCharacters(characterName: String, page: Int) = Retrofit.rickAndMortyApi.searchCharacters(characterName = characterName, page = page)
}