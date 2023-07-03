package com.example.rickandmorty.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.rickandmorty.db.entities.Favorite
import com.example.rickandmorty.db.entities.User
import com.example.rickandmorty.db.dao.IFavoriteDao
import com.example.rickandmorty.db.dao.IUserDao

@Database(entities = [Favorite::class, User::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract val favoriteDao: IFavoriteDao
    abstract val userDao: IUserDao
}