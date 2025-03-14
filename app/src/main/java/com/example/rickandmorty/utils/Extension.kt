package com.example.rickandmorty.utils

val <T : Any > T.TAG: String
    get() { return javaClass.kotlin.toString() }