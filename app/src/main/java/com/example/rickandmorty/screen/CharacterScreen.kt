package com.example.rickandmorty.screen

import android.util.Log
import androidx.activity.compose.BackHandler
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.example.rickandmorty.navigation.Screen

@Composable
fun CharacterScreen(navController: NavController, modifier: Modifier, characterId: Int) {
    Log.d("CharacterScreen", "Character Screen: $characterId")
    BackHandler(enabled = true) {
        navController.navigate(Screen.Home) {
            popUpTo(Screen.Home) { inclusive = true }
        }
    }
}