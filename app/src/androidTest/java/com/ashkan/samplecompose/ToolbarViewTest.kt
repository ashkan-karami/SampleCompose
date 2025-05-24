package com.ashkan.samplecompose

import androidx.activity.ComponentActivity
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.ashkan.samplecompose.ui.components.EdgeToEdgeToolbar
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class ToolbarViewTest {

    @get:Rule
    val composeTestRule = createAndroidComposeRule<ComponentActivity>()

    @Test
    fun edgeToEdgeToolbar_TitleDisplays() {
        val fakeTitle = "fake title"
        composeTestRule.setContent {
            EdgeToEdgeToolbar(
                fakeTitle
            )
        }

        composeTestRule
            .onNodeWithText(fakeTitle)
            .assertIsDisplayed()
    }
}