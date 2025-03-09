package com.example.rickandmorty.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.rickandmorty.models.CharactersResponse
import com.example.rickandmorty.repository.AppRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class MainViewModel : ViewModel(), KoinComponent {
    private val appRepository: AppRepository by inject()

    private val _charactersList = MutableStateFlow<CharactersResponse?>(null)
    val charactersList: StateFlow<CharactersResponse?> = _charactersList

    private val _isLoading = MutableStateFlow(true)
    val isLoading: StateFlow<Boolean> = _isLoading

    fun getCharacters(page: Int) {
        viewModelScope.launch {
            _isLoading.value = true
            // Simulate network call
            val response = fetchCharacters(page)
            _charactersList.value = response
            _isLoading.value = false
        }
    }

    private suspend fun fetchCharacters(page: Int): CharactersResponse {
        return appRepository.getCharacters(page = page)
    }
}