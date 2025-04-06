package com.example.rickandmorty.ui.screen.character

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import coil3.compose.AsyncImage
import com.example.rickandmorty.navigation.Screen
import com.example.rickandmorty.ui.shared.LinearProgress
import org.koin.androidx.compose.koinViewModel

@Composable
fun CharacterScreen(navController: NavController, modifier: Modifier, characterId: Int) {
    val viewModel: CharacterViewModel = koinViewModel()
    val state = viewModel.state.collectAsStateWithLifecycle()
    viewModel.onEvent(event = CharacterEvent.FETCH_CHARACTER, id = characterId)

    Box(
        modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        if (state.value.isLoading) {
            LinearProgress(modifier = modifier)
        }
        when {
            state.value.character.name.isNotEmpty() -> {
                AsyncImage(
                    model = state.value.character.image,
                    modifier = modifier
                        .clip(RoundedCornerShape(24.dp))
                        .width(200.dp)
                        .height(200.dp),
                    contentScale = ContentScale.Fit,
                    contentDescription = "${state.value.character.name} image",
                )
            }
        }
    }

    BackHandler(enabled = true) {
        navController.navigate(Screen.Home) {
            popUpTo(Screen.Home) { inclusive = true }
        }
    }
}