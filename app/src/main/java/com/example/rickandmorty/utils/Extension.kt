package com.example.rickandmorty.utils

val Any.TAG: String
    get() {
        return if (javaClass.isAnonymousClass)
            javaClass.name
        else
            javaClass.simpleName
    }