package com.example.rickandmorty.ui.shared

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FormatListNumbered
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material.icons.outlined.FormatListNumbered
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
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
import androidx.compose.ui.text.font.FontWeight
import androidx.navigation.compose.rememberNavController

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopAppBar(modifier: Modifier) {
    CenterAlignedTopAppBar(
        modifier = modifier,
        title = {
            Text(
                color = MaterialTheme.colorScheme.onPrimary,
                text = "Characters",
                fontWeight = FontWeight.SemiBold
            )
        },
        colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
            containerColor = MaterialTheme.colorScheme.background
        )
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BottomNavBar(modifier: Modifier) {
    var selectedItem by remember { mutableIntStateOf(0) }
    val navController = rememberNavController()

    val items = listOf("Characters", "Favorite", "Profile")
    val selectedIcons = listOf(Icons.Filled.FormatListNumbered, Icons.Filled.Favorite, Icons.Filled.Person)
    val unselectedIcons = listOf(Icons.Outlined.FormatListNumbered, Icons.Outlined.FavoriteBorder, Icons.Outlined.Person)

    NavigationBar(
        containerColor = MaterialTheme.colorScheme.background
    ) {
        items.forEachIndexed { index, item ->
            NavigationBarItem(
                icon = {
                    Icon(
                        if (selectedItem == index) selectedIcons[index] else unselectedIcons[index],
                        contentDescription = item,
                    )
                },
                label = { Text(item) },
                selected = selectedItem == index,
                colors = NavigationBarItemDefaults.colors().copy(
                    selectedIndicatorColor = MaterialTheme.colorScheme.secondary,
                    selectedIconColor = MaterialTheme.colorScheme.primary,
                    unselectedIconColor = MaterialTheme.colorScheme.secondary,
                ),
                onClick = { selectedItem = index }
            )
        }
    }
}