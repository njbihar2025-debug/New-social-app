package com.example

import android.content.Context
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performScrollToNode
import androidx.test.core.app.ApplicationProvider
import com.example.ui.navigation.ApnaSupaulApp
import com.example.ui.theme.ApnaSupaulTheme
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [36], qualifiers = "w411dp-h915dp")
class ExampleRobolectricTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun `read app name from context`() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        val appName = context.getString(R.string.app_name)
        assertEquals("Apna Supaul", appName)
    }

    @Test
    fun `home screen shows all five categories on launch`() {
        composeTestRule.setContent {
            ApnaSupaulTheme {
                ApnaSupaulApp()
            }
        }

        composeTestRule.onNodeWithTag("category_card_local_news").assertIsDisplayed()
        composeTestRule.onNodeWithTag("category_card_banking_digital").assertIsDisplayed()
        composeTestRule.onNodeWithTag("category_card_govt_jobs").assertIsDisplayed()
        composeTestRule.onNodeWithTag("category_card_business_directory").assertIsDisplayed()
        composeTestRule.onNodeWithTag("category_card_ads_promotion").assertIsDisplayed()
    }

    @Test
    fun `clicking each category navigates to its screen`() {
        composeTestRule.setContent {
            ApnaSupaulTheme {
                ApnaSupaulApp()
            }
        }

        composeTestRule.onNodeWithTag("category_card_local_news").performClick()
        composeTestRule.onNodeWithTag("top_bar_back_button").assertIsDisplayed()
        composeTestRule.onNodeWithTag("news_card_news_1").assertIsDisplayed()
        composeTestRule.onNodeWithTag("top_bar_back_button").performClick()

        composeTestRule.onNodeWithTag("category_card_banking_digital").performClick()
        composeTestRule.onNodeWithTag("banking_tab_digital_services").assertIsDisplayed()
        composeTestRule.onNodeWithTag("top_bar_back_button").performClick()

        composeTestRule.onNodeWithTag("category_card_govt_jobs").performClick()
        composeTestRule.onNodeWithTag("jobs_tab_LATEST_JOB").assertIsDisplayed()
        composeTestRule.onNodeWithTag("top_bar_back_button").performClick()

        composeTestRule.onNodeWithTag("category_card_business_directory").performClick()
        composeTestRule.onNodeWithTag("add_business_fab").assertIsDisplayed()
        composeTestRule.onNodeWithTag("top_bar_back_button").performClick()

        composeTestRule.onNodeWithTag("category_card_ads_promotion").performClick()
        composeTestRule.onNodeWithTag("promo_offer_card_promo_1").assertIsDisplayed()
        composeTestRule.onNodeWithTag("ads_promo_list")
            .performScrollToNode(androidx.compose.ui.test.hasTestTag("submit_ad_inquiry_button"))
        composeTestRule.onNodeWithTag("submit_ad_inquiry_button").assertIsDisplayed()
    }

    @Test
    fun `clicking home calling button opens app calling sheet`() {
        composeTestRule.setContent {
            ApnaSupaulTheme {
                ApnaSupaulApp()
            }
        }

        composeTestRule.onNodeWithTag("home_calling_button").performClick()
        composeTestRule.onNodeWithTag("app_calling_sheet").assertIsDisplayed()
        composeTestRule.onNodeWithTag("calling_dial_custom_button").assertIsDisplayed()
    }
}
