package com.example.rickandmorty.ui

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.rickandmorty.models.Character
import com.example.rickandmorty.repository.CharactersRepository
import com.example.rickandmorty.utils.TAG
import com.example.rickandmorty.utils.ViewState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class MainViewModel : ViewModel(), KoinComponent {
    private val charactersRepository: CharactersRepository by inject()

    fun isLoading() {
        updateViewState(ViewState.loading(MainActivityViewState(isLoading = true)))
    }

    fun fetchCharacters(page: Int) {
        viewModelScope.launch {
            charactersRepository.getCharacters(page = page)
                .onSuccess { characters ->
                    updateViewState(ViewState.success(MainActivityViewState(
                        isLoading = false,
                        characters = characters.results
                    )))
                    Log.d(TAG, "Successfully fetched characters: ${characters.results.size}")
                }
                .onFailure { throwable ->
                    updateViewState(ViewState.error(message = throwable.localizedMessage))
                    Log.e(TAG, "Error fetch characters: ${throwable.localizedMessage}")
                }
        }
    }

    private val _state = MutableStateFlow<ViewState<MainActivityViewState>>(ViewState.idle(null))
    val state: StateFlow<ViewState<MainActivityViewState>> = _state.asStateFlow()

    fun updateViewState(viewState: ViewState<MainActivityViewState>) {
        _state.update {
            when (viewState) {
                is ViewState.Idle -> ViewState.idle(data = viewState.data)
                is ViewState.Loading -> ViewState.loading(data = viewState.data)
                is ViewState.Success -> ViewState.success(data = viewState.data)
                is ViewState.Error -> ViewState.error(message = viewState.message)
            }
        }
    }

    fun dropViewState() {
        _state.update {
            ViewState.idle(null)
        }
    }
}

data class MainActivityViewState(
    val characters: List<Character> = emptyList(),
    val isLoading: Boolean = false,
    val error: String = ""
)
