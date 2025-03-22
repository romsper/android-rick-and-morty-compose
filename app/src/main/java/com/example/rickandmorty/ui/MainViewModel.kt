package com.example.rickandmorty.ui

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.rickandmorty.models.Character
import com.example.rickandmorty.repository.CharactersRepository
import com.example.rickandmorty.utils.TAG
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class MainViewModel : ViewModel(), KoinComponent {
    private val charactersRepository: CharactersRepository by inject()

    private val _state = MutableStateFlow(MainActivityViewState())
    var state = _state
        .onStart { fetchCharacters(page = 1) }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000L),
            initialValue = MainActivityViewState()
        )

    fun onEvent(event: MainEvent, page: Int?) {
        Log.d(TAG, "Event: $event")
        when (event) {
            MainEvent.IS_LOADING -> doLoading()
            MainEvent.FETCH_CHARACTERS -> page?.let { fetchCharacters(page) }
        }
    }

    fun doLoading() {
        _state.value = _state.value.copy(isLoading = true)
    }

    fun fetchCharacters(page: Int) {
        doLoading()
        viewModelScope.launch {
            charactersRepository.getCharacters(page = page)
                .onSuccess { characters ->
                    _state.value = _state.value.copy(
                        pagesCount = characters.info.pages,
                        nextPage = characters.info.next.filter { it.isDigit() }.toIntOrNull(),
                        characters = _state.value.characters + characters.results,
                        isLoading = false
                    )
                    Log.d(TAG, "Successfully fetched characters: ${_state.value.characters.size}")
                }
                .onFailure { throwable ->
                    _state.value = _state.value.copy(
                        isLoading = false,
                        error = throwable.message ?: "An error occurred"
                    )
                }
        }
    }
}

data class MainActivityViewState(
    val characters: List<Character> = emptyList(),
    val nextPage: Int? = 1,
    val pagesCount: Int = 1,
    var isLoading: Boolean = true,
    val error: String = ""
)

enum class MainEvent {
    IS_LOADING,
    FETCH_CHARACTERS
}
