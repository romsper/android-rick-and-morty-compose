package com.example.rickandmorty.ui.shared

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FormatListNumbered
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material.icons.outlined.FormatListNumbered
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.kyant.backdrop.backdrops.LayerBackdrop
import com.kyant.backdrop.backdrops.layerBackdrop

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopAppBar(modifier: Modifier, title: String) {
    CenterAlignedTopAppBar(
        modifier = modifier,
        title = {
            Text(
                modifier = Modifier.testTag("titleTopAppBar"),
                color = MaterialTheme.colorScheme.onPrimary,
                text = title,
                fontWeight = FontWeight.SemiBold,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
            )
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.background
        )
    )
}

@Composable
fun BottomNavBar(backdrop: LayerBackdrop, startIndex: Int) {
    var selectedItem by remember { mutableIntStateOf(startIndex) }

    val items = listOf("Characters", "Favorite", "Profile")
    val selectedIcons = listOf(Icons.Filled.FormatListNumbered, Icons.Filled.Favorite, Icons.Filled.Settings)
    val unselectedIcons = listOf(Icons.Outlined.FormatListNumbered, Icons.Outlined.FavoriteBorder, Icons.Outlined.Settings)

    NavigationBar(
        modifier = Modifier.layerBackdrop(backdrop),
        containerColor = Color.Transparent
    ) {
        items.forEachIndexed { index, item ->
            NavigationBarItem(
                icon = {
                    Icon(
                        if (selectedItem == index) selectedIcons[index] else unselectedIcons[index],
                        contentDescription = item,
                    )
                },
                selected = selectedItem == index,
                colors = NavigationBarItemDefaults.colors().copy(
                    selectedIconColor = MaterialTheme.colorScheme.primary,
                    unselectedIconColor = MaterialTheme.colorScheme.secondary,
                    selectedIndicatorColor = Color.Transparent
                ),
                onClick = { selectedItem = index }
            )
        }
    }
}

@Composable
fun LinearProgress(modifier: Modifier) {
    LinearProgressIndicator(
        modifier = modifier
            .fillMaxWidth()
            .height(4.dp),
        trackColor = MaterialTheme.colorScheme.background,
        color = MaterialTheme.colorScheme.primary
    )
}