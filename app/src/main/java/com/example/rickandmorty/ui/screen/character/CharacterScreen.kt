package com.example.rickandmorty.ui.screen.character

import android.util.Log
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SuggestionChip
import androidx.compose.material3.SuggestionChipDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import coil3.compose.AsyncImage
import com.example.rickandmorty.models.SingleCharacterResponse
import com.example.rickandmorty.navigation.Screen
import com.example.rickandmorty.ui.shared.LinearProgress
import com.example.rickandmorty.ui.shared.TopAppBar
import org.koin.androidx.compose.koinViewModel

@Composable
fun CharacterScreen(navController: NavController?, modifier: Modifier, characterId: Int) {
    val viewModel: CharacterViewModel = koinViewModel()
    val state = viewModel.state.collectAsStateWithLifecycle()
    viewModel.onEvent(event = CharacterEvent.FETCH_CHARACTER, id = characterId)

    Scaffold(
        modifier = modifier.fillMaxSize(),
        topBar = { TopAppBar(modifier = modifier, title = state.value.character.name) },
        content = { paddingValues ->
            if (state.value.isLoading) {
                LinearProgress(modifier = modifier)
            } else {
                Log.d("TAG", "Character: ${state.value.character}")
                CharacterDetails(modifier.padding(paddingValues), character = state.value.character)
            }
        }
    )

    BackHandler(enabled = true) {
        navController?.navigate(Screen.Home) {
            popUpTo(Screen.Home) { inclusive = true }
        }
    }
}

@Composable
fun CharacterDetails(
    modifier: Modifier, character: SingleCharacterResponse
) {
    ConstraintLayout(modifier = modifier) {
        val (image, id, box) = createRefs()

        AsyncImage(
            model = character.image,
            contentScale = ContentScale.Fit,
            contentDescription = "${character.name} image",
            modifier = Modifier
                .zIndex(2f)
                .size(150.dp)
                .clip(shape = RoundedCornerShape(24.dp))
                .constrainAs(image) {
                    top.linkTo(parent.top, margin = 16.dp)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                }
        )

        SuggestionChip(
            modifier = Modifier
                .zIndex(3f)
                .constrainAs(id) {
                    bottom.linkTo(image.bottom, margin = (-18).dp)
                    end.linkTo(image.end, margin = (-24).dp)
                },
            colors = SuggestionChipDefaults.suggestionChipColors(
                containerColor = MaterialTheme.colorScheme.primary,
                labelColor = MaterialTheme.colorScheme.onPrimary
            ),
            border = null,
            onClick = { },
            label = { Text(text = "#${character.id}") }
        )

        Box(
            modifier = Modifier
                .zIndex(1f)
                .fillMaxSize()
                .clip(shape = RoundedCornerShape(24.dp, 24.dp, 0.dp, 0.dp))
                .background(color = MaterialTheme.colorScheme.secondary)
                .constrainAs(box) {
                    top.linkTo(parent.top, margin = 110.dp)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                },
        ) {
            Column(modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)) {

                Spacer(modifier = Modifier.height(100.dp))

                Text(
                    text = character.name,
                    fontSize = 24.sp,
                    color = MaterialTheme.colorScheme.background,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }
        }
    }
}
