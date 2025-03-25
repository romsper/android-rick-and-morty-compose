package com.example.rickandmorty.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.rickandmorty.navigation.Navigation
import com.example.rickandmorty.ui.shared.BottomNavBar
import com.example.rickandmorty.ui.shared.TopAppBar
import com.example.rickandmorty.ui.theme.RickAndMortyTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()
        setContent {
            RickAndMortyTheme {
                val modifier = Modifier.background(MaterialTheme.colorScheme.background)

                Scaffold(
                    modifier = modifier.fillMaxSize(),
                    topBar = { TopAppBar(modifier = modifier) },
                    bottomBar = { BottomNavBar(modifier = modifier) },
                    content = {
                        MainScreen(
                            modifier = modifier.padding(
                                top = it.calculateTopPadding(),
                                bottom = it.calculateBottomPadding()
                            )
                        )
                    }
                )
            }
        }
    }
}

@Composable
fun MainScreen(modifier: Modifier) {
    val navController: NavHostController = rememberNavController()
    Navigation(navController = navController, modifier = modifier)
}