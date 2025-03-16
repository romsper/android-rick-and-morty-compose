package com.example.rickandmorty.ui

import android.content.res.Configuration
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.background
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
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
import com.example.rickandmorty.models.Character
import com.example.rickandmorty.models.Location
import com.example.rickandmorty.models.Origin
import com.example.rickandmorty.ui.shared.TopAppBar
import com.example.rickandmorty.ui.theme.RickAndMortyTheme

class MainActivity : ComponentActivity() {
    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel.onEvent(MainEvent.FETCH_CHARACTERS, 1)

        setContent {
            RickAndMortyTheme {
                val state = viewModel.state
                App(modifier = Modifier.fillMaxSize(), state = state)
            }
        }
    }
}

@Composable
fun App(modifier: Modifier = Modifier, state: MainActivityViewState) {
    RickAndMortyTheme {
        Scaffold(
            modifier = modifier.background(MaterialTheme.colorScheme.background),
            topBar = { TopAppBar(modifier = modifier) },
            content = {
                Column(
                    modifier = Modifier
                        .padding(
                            top = it.calculateTopPadding(),
                            bottom = it.calculateBottomPadding()
                        )
                        .fillMaxSize()
                ) {
                    if (state.isLoading) {
                        LinearProgressIndicator(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(4.dp),
                            trackColor = MaterialTheme.colorScheme.background,
                            color = MaterialTheme.colorScheme.primary
                        )
                    }
                    when {
                        state.characters.isNotEmpty() -> {
                            CharactersList(
                                modifier = modifier,
                                characters = state.characters
                            )
                        }

                        state.error.isNotEmpty() -> {
                            Text(
                                text = state.error,
                                color = Color.Red,
                                modifier = modifier.padding(16.dp)
                            )
                        }
                    }
                }
            }
        )
    }
}

@Composable
fun CharactersList(
    modifier: Modifier = Modifier,
    characters: List<Character> = emptyList(),
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
                    .padding(vertical = 8.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Start
            ) {
                AsyncImage(
                    model = item.image,
                    modifier = Modifier
                        .clip(RoundedCornerShape(16.dp))
                        .width(100.dp)
                        .height(100.dp),
                    contentScale = ContentScale.Fit,
                    contentDescription = "Character's image",
                )

                Text(
                    modifier = Modifier
                        .padding(horizontal = 16.dp)
                        .weight(1f),
                    color = MaterialTheme.colorScheme.onPrimary,
                    text = item.name,
                    fontSize = 20.sp,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )

                Icon (
                    imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
                    modifier = Modifier
                        .width(30.dp)
                        .height(30.dp)
                        .clickable { },
                    contentDescription = "Favorite button",
                    tint = MaterialTheme.colorScheme.primary
                )
            }
        }
    }
}

@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun MainActivityPreview() {
    RickAndMortyTheme {
        App(
            modifier = Modifier.fillMaxSize(), state = MainActivityViewState(
                characters = listOf(
                    Character(
                        created = "",
                        episode = emptyList(),
                        gender = "",
                        id = 0,
                        image = "",
                        location = Location(
                            name = "",
                            url = ""
                        ),
                        name = "Rick Sanchez Rick Sanchez Rick Sanchez ",
                        origin = Origin(
                            name = "",
                            url = ""
                        ),
                        species = "",
                        status = "",
                        type = "",
                        url = ""
                    )
                ),
                isLoading = false,
                error = "",
            )
        )
    }
}
