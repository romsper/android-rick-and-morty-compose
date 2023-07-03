package com.example.rickandmorty.network

import com.example.rickandmorty.models.CharactersResponse
import com.example.rickandmorty.models.SingleCharacterResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface RickAndMortyApi {

    @GET("character")
    suspend fun getCharacters(@Query("page") page: Int): CharactersResponse

    @GET("character/{characterId}")
    suspend fun getCharacterById(@Path("characterId") characterId: Int): SingleCharacterResponse

    @GET("character")
    suspend fun searchCharacters(@Query("name") characterName: String, @Query("page") page: Int) : CharactersResponse
}