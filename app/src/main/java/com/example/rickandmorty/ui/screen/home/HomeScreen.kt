package com.example.rickandmorty.ui.screen.home

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import coil3.compose.AsyncImage
import com.example.rickandmorty.models.Character
import com.example.rickandmorty.navigation.Screen
import com.example.rickandmorty.ui.shared.BottomNavBar
import com.example.rickandmorty.ui.shared.LinearProgress
import com.example.rickandmorty.ui.shared.TopAppBar
import org.koin.androidx.compose.koinViewModel

@Composable
fun HomeScreen(navController: NavController, modifier: Modifier) {
    val viewModel: HomeViewModel = koinViewModel()
    val state = viewModel.state.collectAsStateWithLifecycle()

    Scaffold(
        modifier = modifier.fillMaxSize(),
        topBar = { TopAppBar(modifier = modifier) },
        bottomBar = { BottomNavBar(modifier = modifier, startIndex = 0) },
        content = {
            Column(
                modifier = modifier.padding(
                    top = it.calculateTopPadding(),
                    bottom = it.calculateBottomPadding()
                )
            ) {
                if (state.value.isLoading) {
                    LinearProgress(modifier = modifier)
                }
                when {
                    state.value.characters.isNotEmpty() -> {
                        CharactersList(
                            modifier = Modifier,
                            characters = state.value.characters,
                            navController = navController,
                            onNewItems = {
                                LaunchedEffect(true) {
                                    state.value.nextPage?.let {
                                        viewModel.onEvent(
                                            HomeEvent.FETCH_CHARACTERS,
                                            state.value.nextPage
                                        )
                                    }
                                }
                            }
                        )
                    }

                    state.value.error.isNotEmpty() -> {
                        Text(
                            text = state.value.error,
                            color = Color.Red,
                            modifier = modifier.padding(16.dp)
                        )
                    }
                }
            }
        }
    )
}

@Composable
fun CharactersList(
    modifier: Modifier,
    characters: List<Character> = emptyList(),
    navController: NavController,
    onNewItems: @Composable () -> Unit,
) {
    LazyColumn(
        modifier = modifier
            .padding(all = 16.dp)
            .fillMaxWidth()
    ) {
        items(items = characters) { item ->
            Row(
                modifier = modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp)
                    .clickable {
                        navController.navigate(Screen.Character(item.id)) {
                            popUpTo(Screen.Home) { inclusive = true }
                        }
                    },
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Start
            ) {
                AsyncImage(
                    model = item.image,
                    modifier = Modifier
                        .clip(RoundedCornerShape(24.dp))
                        .width(100.dp)
                        .height(100.dp),
                    contentScale = ContentScale.Fit,
                    contentDescription = "Character's image",
                )

                Column(
                    modifier = modifier
                        .padding(horizontal = 16.dp)
                        .weight(1f),
                    verticalArrangement = Arrangement.SpaceBetween
                ) {

                    Text(
                        modifier = modifier,
                        color = MaterialTheme.colorScheme.onPrimary,
                        text = item.name,
                        fontSize = 20.sp,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )

                    Text(
                        modifier = modifier,
                        color = MaterialTheme.colorScheme.onPrimary,
                        text = "Status: ${item.status}",
                        fontSize = 14.sp,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                }

                Icon(
                    imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
                    modifier = Modifier
                        .width(30.dp)
                        .height(30.dp),
                    contentDescription = "Go to character details",
                    tint = MaterialTheme.colorScheme.primary
                )
            }
        }
        item {
            onNewItems()
        }
    }
}
