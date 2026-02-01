package com.example.rickandmorty.ui.screen.home

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeContentPadding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowRight
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
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil3.compose.AsyncImage
import com.example.rickandmorty.models.Character
import com.example.rickandmorty.ui.shared.BottomNavBar
import com.example.rickandmorty.ui.shared.LinearProgress
import com.example.rickandmorty.ui.shared.TopAppBar
import com.kyant.backdrop.backdrops.rememberLayerBackdrop
import com.kyant.backdrop.drawBackdrop
import com.kyant.backdrop.effects.blur
import com.kyant.backdrop.effects.lens
import com.kyant.backdrop.effects.vibrancy
import org.koin.androidx.compose.koinViewModel

@Composable
fun HomeScreen(modifier: Modifier, onNavigate: (id: Int) -> Unit) {
    val viewModel: HomeViewModel = koinViewModel()
    val state = viewModel.state.collectAsStateWithLifecycle()

    Scaffold(
        modifier = modifier.fillMaxSize(),
        topBar = { TopAppBar(modifier = modifier, title = "Characters") },
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
                        ConstraintLayout(Modifier.fillMaxWidth()) {
                            val (list, line, btn) = createRefs()

                            val backdrop = rememberLayerBackdrop()
                            val btnSurfaceColor = MaterialTheme.colorScheme.background.copy(alpha = 0.9f)

                            CharactersList(
                                modifier = Modifier.constrainAs(list) {
                                    top.linkTo(parent.top)
                                    bottom.linkTo(parent.bottom)
                                    start.linkTo(parent.start)
                                    end.linkTo(parent.end)
                                },
                                characters = state.value.characters,
                                onNavigate = { id ->
                                    onNavigate(id)
                                },
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

                            Box(
                                Modifier
                                    .constrainAs(line) {
                                        bottom.linkTo(parent.bottom)
                                        start.linkTo(parent.start)
                                        end.linkTo(btn.start, margin = 4.dp)
                                        width = Dimension.fillToConstraints
                                    }
                                    .safeContentPadding()
                                    .drawBackdrop(
                                        backdrop = backdrop,
                                        shape = { CircleShape },
                                        effects = {
                                            vibrancy()
                                            blur(4f.dp.toPx())
                                            lens(16f.dp.toPx(), 32f.dp.toPx())
                                        },
                                        onDrawSurface = { drawRect(btnSurfaceColor) }
                                    )
                                    .height(64.dp)
                            ) {
                                BottomNavBar(backdrop = backdrop, startIndex = 0)
                            }

                            Box(
                                Modifier
                                    .constrainAs(btn) {
                                        bottom.linkTo(parent.bottom)
                                        end.linkTo(parent.end)
                                    }
                                    .safeContentPadding()
                                    .drawBackdrop(
                                        backdrop = backdrop,
                                        shape = { CircleShape },
                                        effects = {
                                            vibrancy()
                                            blur(4f.dp.toPx())
                                            lens(16f.dp.toPx(), 32f.dp.toPx())
                                        },
                                        onDrawSurface = { drawRect(btnSurfaceColor) }
                                    )
                                    .clip(CircleShape)
                                    .height(64.dp)
                                    .width(65.dp)
                            ) {
                                Icon(
                                    modifier = Modifier
                                        .size(52.dp)
                                        .align(Alignment.Center),
                                    imageVector = Icons.AutoMirrored.Filled.ArrowRight,
                                    tint = MaterialTheme.colorScheme.primary,
                                    contentDescription = "Go to character details"
                                )
                            }
                        }
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
    onNavigate: (id: Int) -> Unit,
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
                        onNavigate(item.id)
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
