package com.example.rickandmorty.ui.screen.character

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
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
                ConstraintLayout(modifier = modifier.padding(paddingValues)) {
                    val (image, box) = createRefs()

                    AsyncImage(
                        model = state.value.character.image,
                        contentScale = ContentScale.Fit,
                        contentDescription = "${state.value.character.name} image",
                        modifier = Modifier
                            .zIndex(2f)
                            .size(150.dp)
                            .clip(shape = RoundedCornerShape(24.dp))
                            .constrainAs(image) {
                                top.linkTo(parent.top, margin = 16.dp)
                                start.linkTo(parent.start)
                                end.linkTo(parent.end)
                            },
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
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = state.value.character.name,
                            fontSize = 24.sp,
                            color = MaterialTheme.colorScheme.background,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                    }
                }
            }
        }
    )

    BackHandler(enabled = true) {
        navController?.navigate(Screen.Home) {
            popUpTo(Screen.Home) { inclusive = true }
        }
    }
}