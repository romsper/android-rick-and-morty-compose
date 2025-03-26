package com.example.rickandmorty.models

import kotlinx.serialization.Serializable

@Serializable
data class Location(
    val name: String,
    val url: String
)

@Serializable
data class Origin(
    val name: String,
    val url: String
)