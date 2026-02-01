package com.example.rickandmorty.screens

import androidx.compose.ui.test.SemanticsNodeInteractionsProvider
import com.kaspersky.components.composesupport.core.KNode
import io.github.kakaocup.compose.node.element.ComposeScreen

class HomeScreen(semanticsProvider: SemanticsNodeInteractionsProvider) :
    ComposeScreen<HomeScreen>(
        semanticsProvider = semanticsProvider,
        viewBuilderAction = { hasTestTag("ComposeMainScreen") }
    ) {

    val title: KNode = child { hasTestTag("titleTopAppBar") }
}