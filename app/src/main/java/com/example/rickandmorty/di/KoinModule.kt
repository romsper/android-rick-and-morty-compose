package com.example.rickandmorty.di

import android.app.Application
import android.content.Context
import androidx.room.Room
import com.example.rickandmorty.db.AppDatabase
import com.example.rickandmorty.repository.AppRepository
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val koinModule = module {
    single<Context> { Application().applicationContext }

    singleOf(::AppRepository)

    single<AppDatabase> {
        Room.databaseBuilder(
            get<Context>(),
            AppDatabase::class.java,
            "rm_database"
        ).build()
    }
}