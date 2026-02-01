package com.example.rickandmorty

import androidx.compose.ui.test.junit4.v2.createAndroidComposeRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.rickandmorty.screens.HomeScreen
import com.example.rickandmorty.ui.MainActivity
import com.kaspersky.components.composesupport.config.withComposeSupport
import com.kaspersky.kaspresso.kaspresso.Kaspresso
import com.kaspersky.kaspresso.testcases.api.testcase.TestCase
import io.github.kakaocup.compose.node.element.ComposeScreen.Companion.onComposeScreen
import org.junit.Ignore
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class HomeScreenTest : TestCase(kaspressoBuilder = Kaspresso.Builder.withComposeSupport()) {

    @get:Rule
    val composeTestRule = createAndroidComposeRule<MainActivity>()

    @Test
    fun testTitleOnHomeScreen() = run {
        step("Check title text") {
            onComposeScreen<HomeScreen>(composeTestRule) {
                title {
                    assertIsDisplayed()
                    assertTextEquals("Characters")
                }
            }
        }
    }

    @Test
    @Ignore("The test is not implemented yet")
    fun testSelectedCharacter() = run {
        step("") {
            onComposeScreen<HomeScreen>(composeTestRule) {
            }
        }
    }
}