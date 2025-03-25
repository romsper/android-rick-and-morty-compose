package com.example.rickandmorty.di

import android.app.Application
import android.content.Context
import androidx.room.Room
import com.example.rickandmorty.db.AppDatabase
import com.example.rickandmorty.network.Retrofit
import com.example.rickandmorty.network.RickAndMortyApi
import com.example.rickandmorty.repository.CharactersRepository
import com.example.rickandmorty.repository.DataRepository
import com.example.rickandmorty.screen.HomeViewModel
import org.koin.core.module.dsl.singleOf
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val koinModule = module {
    single<Context> { Application().applicationContext }

    singleOf(::DataRepository)
    singleOf(::CharactersRepository)

    viewModelOf(::HomeViewModel)

    single<AppDatabase> {
        Room.databaseBuilder(
            get<Context>(),
            AppDatabase::class.java,
            "rm_database"
        ).build()
    }

    single<RickAndMortyApi> {
        Retrofit.rickAndMortyApi
    }
}