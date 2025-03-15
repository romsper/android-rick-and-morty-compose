package com.example.rickandmorty.ui

import android.content.res.Configuration
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.rickandmorty.models.Character
import com.example.rickandmorty.models.Location
import com.example.rickandmorty.models.Origin
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
                        .padding(top = it.calculateTopPadding())
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopAppBar(modifier: Modifier) {
    CenterAlignedTopAppBar(
        title = {
            Text(
                color = MaterialTheme.colorScheme.onPrimary,
                text = "Characters"
            )
        },
        colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
            containerColor = MaterialTheme.colorScheme.background
        )
    )
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
                    .padding(vertical = 8.dp)
                    .fillParentMaxWidth()
            ) {
                Image(
                    modifier = modifier
                        .width(50.dp)
                        .height(50.dp),
                    painter = painterResource(id = androidx.core.R.drawable.ic_call_decline),
                    contentDescription = "Character's image"
                )
            }
            Row(
                modifier = modifier
                    .padding(vertical = 8.dp)
                    .fillParentMaxWidth()
            ) {
                Text(
                    modifier = modifier
                        .padding(horizontal = 8.dp)
                        .wrapContentWidth()
                        .align(Alignment.CenterVertically),
                    color = MaterialTheme.colorScheme.onPrimary,
                    text = item.name
                )
            }
            Row(
                modifier = modifier
                    .padding(vertical = 8.dp)
                    .fillParentMaxWidth()
            ) {
                Image(
                    modifier = modifier
                        .width(30.dp)
                        .width(30.dp)
                        .align(Alignment.CenterVertically)
                        .clickable { },
                    painter = painterResource(id = androidx.core.R.drawable.ic_call_answer),
                    contentDescription = "Favorite button"
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
                        name = "",
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
