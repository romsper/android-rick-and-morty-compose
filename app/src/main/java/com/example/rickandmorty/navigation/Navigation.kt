package com.example.rickandmorty.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation3.runtime.NavBackStack
import androidx.navigation3.runtime.NavKey
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.runtime.rememberSaveableStateHolderNavEntryDecorator
import androidx.navigation3.ui.NavDisplay
import com.example.rickandmorty.ui.screen.SplashScreen
import com.example.rickandmorty.ui.screen.character.CharacterScreen
import com.example.rickandmorty.ui.screen.home.HomeScreen
import kotlinx.serialization.Serializable

@Composable
fun Navigation(modifier: Modifier, backStack: NavBackStack<NavKey>) {
    NavDisplay(
        backStack = backStack,
        onBack = { backStack.removeLastOrNull() },
        entryDecorators = listOf(
            rememberSaveableStateHolderNavEntryDecorator(),
        ),
        entryProvider = entryProvider {
            entry<Screen.Splash> {
                SplashScreen(modifier = modifier) {
                    backStack.add(Screen.Home)
                    backStack.remove(Screen.Splash)
                }
            }
            entry<Screen.Home> {
                HomeScreen(modifier = modifier) {
                    backStack.add(Screen.Character(it))
                }
            }
            entry<Screen.Character> { key ->
                CharacterScreen(characterId = key.characterId, modifier = modifier) {
                    backStack.remove(Screen.Character(key.characterId))
                }
            }
        }
    )
}

@Serializable
sealed class Screen : NavKey {
    @Serializable
    object Splash : Screen()

    @Serializable
    object Home : Screen()

    @Serializable
    data class Character(val characterId: Int) : Screen()
}