package com.example.rickandmorty.ui.screen.character

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.rickandmorty.models.SingleCharacterResponse
import com.example.rickandmorty.repository.CharactersRepository
import com.example.rickandmorty.utils.TAG
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class CharacterViewModel : ViewModel(), KoinComponent {
    private val charactersRepository: CharactersRepository by inject()

    private val _state = MutableStateFlow(CharacterViewState())
    var state = _state
        .onStart { doLoading() }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000L),
            initialValue = CharacterViewState()
        )

    fun onEvent(event: CharacterEvent, id: Int?) {
        Log.d(TAG, "Event: $event")
        when (event) {
            CharacterEvent.IS_LOADING -> doLoading()
            CharacterEvent.FETCH_CHARACTER -> id?.let { fetchCharacterById(id) }
        }
    }

    fun doLoading() {
        _state.value = _state.value.copy(isLoading = true)
    }

    fun fetchCharacterById(id: Int) {
        doLoading()
        viewModelScope.launch {
            charactersRepository.getCharacterById(characterId = id)
                .onSuccess { character ->
                    _state.value = _state.value.copy(
                        character = character,
                        isLoading = false
                    )
                    Log.d(TAG, "Successfully fetched character: ${_state.value.character.name}")
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

data class CharacterViewState(
    val character: SingleCharacterResponse = SingleCharacterResponse(),
    var isLoading: Boolean = true,
    val error: String = ""
)

enum class CharacterEvent {
    IS_LOADING,
    FETCH_CHARACTER
}
