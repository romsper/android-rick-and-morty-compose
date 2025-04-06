package com.example.rickandmorty.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.example.rickandmorty.ui.screen.SplashScreen
import com.example.rickandmorty.ui.screen.character.CharacterScreen
import com.example.rickandmorty.ui.screen.home.HomeScreen
import kotlinx.serialization.Serializable

@Composable
fun Navigation(navController: NavHostController, modifier: Modifier) {
    NavHost(navController = navController, startDestination = Screen.Splash) {
        composable<Screen.Splash> {
            SplashScreen(navController = navController, modifier = modifier)
        }
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
    object Splash : Screen()
    @Serializable
    object Home : Screen()
    @Serializable
    data class Character(val characterId: Int) : Screen()
}