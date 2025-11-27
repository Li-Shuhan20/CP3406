package com.example.androidstarter.ui.community

import androidx.activity.ComponentActivity
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithText
import org.junit.Rule
import org.junit.Test

class CommunityScreenTest {

    @get:Rule
    val composeRule = createAndroidComposeRule<ComponentActivity>()

    @Test
    fun communityScreen_showsHeaderAndSectionTitle() {
        composeRule.setContent {
            CommunityScreen()
        }

        // 标题
        composeRule
            .onNodeWithText("Community")
            .assertIsDisplayed()

        // 讨论区块标题
        composeRule
            .onNodeWithText("Latest discussions")
            .assertIsDisplayed()
    }
}