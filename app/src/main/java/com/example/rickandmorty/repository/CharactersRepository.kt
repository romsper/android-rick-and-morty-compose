package com.example.rickandmorty.repository

import com.example.rickandmorty.network.RickAndMortyApi
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

open class CharactersRepository: KoinComponent {
    private val rickAndMortyApi: RickAndMortyApi by inject()

    suspend fun getCharacters(page: Int) = rickAndMortyApi.getCharacters(page = page)
    suspend fun getCharacterById(characterId: Int) = rickAndMortyApi.getCharacterById(characterId = characterId)
    suspend fun searchCharacters(characterName: String, page: Int) = rickAndMortyApi.searchCharacters(characterName = characterName, page = page)
}