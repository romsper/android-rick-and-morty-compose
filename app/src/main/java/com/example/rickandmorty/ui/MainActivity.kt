package com.example.rickandmorty.ui

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.Image
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
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.rickandmorty.models.Character
import com.example.rickandmorty.ui.theme.RickAndMortyTheme
import com.example.rickandmorty.utils.ViewState

class MainActivity : ComponentActivity() {
    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel.isLoading()
        viewModel.fetchCharacters(1)

        setContent {
            RickAndMortyTheme {
                App(modifier = Modifier.fillMaxSize(), viewModel = viewModel)
            }
        }
    }

    @Composable
    fun App(modifier: Modifier = Modifier, viewModel: MainViewModel) {
        val viewState by viewModel.state.collectAsState()

        RickAndMortyTheme {
            Scaffold(
                topBar = { TopAppBar() },
                content = {
                    Column(
                        modifier = Modifier
                            .padding(top = it.calculateTopPadding())
                            .fillMaxSize(),
                        content = {
                            when (viewState) {
                                is ViewState.Loading -> {
                                    LinearProgressIndicator(modifier = modifier)
                                }

                                is ViewState.Success -> {
                                    CharactersList(
                                        modifier = modifier,
                                        characters = (viewState as ViewState.Success<MainActivityViewState>).data.characters
                                    )
                                }

                                is ViewState.Error -> {
                                    Text(
                                        text = (viewState as ViewState.Error).message,
                                        color = Color.Red,
                                        modifier = modifier.padding(16.dp)
                                    )
                                }

                                else -> {
                                    // Handle other states if necessary
                                }
                            }
                        }
                    )
                }
            )
        }
    }

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun TopAppBar() {
        CenterAlignedTopAppBar(title = {
            Text(text = "Characters")
        })
    }

    @Preview(showBackground = true, uiMode = UI_MODE_NIGHT_YES)
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
                        color = Color.Black,
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
}