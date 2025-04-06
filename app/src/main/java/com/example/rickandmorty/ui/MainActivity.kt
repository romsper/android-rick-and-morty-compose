package com.example.rickandmorty.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.rickandmorty.navigation.Navigation
import com.example.rickandmorty.ui.theme.RickAndMortyTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()
        setContent {
            RickAndMortyTheme {
                val modifier = Modifier.background(MaterialTheme.colorScheme.background)
                MainScreen(modifier = modifier)
            }
        }
    }
}

@Composable
fun MainScreen(modifier: Modifier) {
    val navController: NavHostController = rememberNavController()
    Navigation(navController = navController, modifier = modifier)
}