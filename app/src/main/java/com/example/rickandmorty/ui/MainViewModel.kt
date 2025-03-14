package com.example.rickandmorty.ui

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.rickandmorty.models.Character
import com.example.rickandmorty.repository.CharactersRepository
import com.example.rickandmorty.utils.TAG
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class MainViewModel : ViewModel(), KoinComponent {
    private val charactersRepository: CharactersRepository by inject()

    var state by mutableStateOf(MainActivityViewState())
        private set

    fun onEvent(event: MainEvent, page: Int) {
        Log.d(TAG, "Event: $event")
        when (event) {
            MainEvent.IS_LOADING -> isLoading()
            MainEvent.FETCH_CHARACTERS -> fetchCharacters(page)
        }
    }

    fun isLoading() {
        state = state.copy(isLoading = true)
    }

    fun fetchCharacters(page: Int) {
        viewModelScope.launch {
            charactersRepository.getCharacters(page = page)
                .onSuccess { characters ->
                    state = state.copy(
                        characters = characters.results,
                        isLoading = false
                    )
                    Log.d(TAG, "Successfully fetched characters: ${characters.results.size}")
                }
                .onFailure { throwable ->
                    state = state.copy(
                        isLoading = false,
                        error = throwable.message?: "An error occurred"
                    )
                }
        }
    }
}

data class MainActivityViewState(
    val characters: List<Character> = emptyList(),
    val isLoading: Boolean = true,
    val error: String = ""
)

enum class MainEvent {
    IS_LOADING,
    FETCH_CHARACTERS
}
