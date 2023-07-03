package com.example.rickandmorty.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.rickandmorty.db.entities.Favorite
import com.example.rickandmorty.repository.AppRepository
import kotlinx.coroutines.launch
import javax.inject.Inject


class MainViewModel @Inject constructor(
    private val appRepository: AppRepository
) : ViewModel() {

    companion object {
        var characterName: String = ""
    }

    private val _favoriteListLiveData = MutableLiveData<List<Favorite>>()
    val favoriteList: LiveData<List<Favorite>> = _favoriteListLiveData

    fun fetchFavorites() = viewModelScope.launch {
        val favoriteList = appRepository.getFavoritesDB()
        _favoriteListLiveData.postValue(favoriteList)
    }

    fun addFavorite(favorite: Favorite) = viewModelScope.launch {
        appRepository.addFavoriteDB(favorite = favorite)
    }

    fun removeFavoriteItem(characterId: Int) = viewModelScope.launch {
        appRepository.removeFavoriteDB(characterId = characterId)
    }

}