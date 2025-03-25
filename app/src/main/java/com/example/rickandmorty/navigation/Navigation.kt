package com.example.rickandmorty.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.example.rickandmorty.screen.CharacterScreen
import com.example.rickandmorty.screen.HomeScreen
import kotlinx.serialization.Serializable

@Composable
fun Navigation(navController: NavHostController, modifier: Modifier) {
    NavHost(navController = navController, startDestination = Screen.Home) {
        composable<Screen.Home> {
             HomeScreen(navController = navController, modifier = modifier)
        }
        composable<Screen.Character> {
            val args = it.toRoute<Screen.Character>()
            CharacterScreen(navController = navController, modifier = modifier, characterId = args.characterId)
        }
    }
}

@Serializable
sealed class Screen {
    @Serializable
    object Home : Screen()

    @Serializable
    data class Character(val characterId: Int) : Screen()
}