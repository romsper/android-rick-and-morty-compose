package com.example.rickandmorty.models

import kotlinx.serialization.Serializable

@Serializable
data class CharactersResponse(
    val info: Info,
    val results: List<Character>
)

@Serializable
data class Info(
    val count: Int,
    val next: String?,
    val pages: Int,
    val prev: String?
)

@Serializable
data class Character(
    val created: String = "",
    val episode: List<String> = emptyList<String>(),
    val gender: String = "",
    val id: Int = 0,
    val image: String = "",
    val location: Location = Location("", ""),
    val name: String = "",
    val origin: Origin = Origin("", ""),
    val species: String = "",
    val status: String = "",
    val type: String = "",
    val url: String = ""
)