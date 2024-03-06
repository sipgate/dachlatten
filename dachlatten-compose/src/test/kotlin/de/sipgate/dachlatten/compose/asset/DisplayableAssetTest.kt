package de.sipgate.dachlatten.compose.asset

import androidx.compose.ui.test.junit4.createComposeRule
import de.sipgate.dachlatten.asset.DisplayableAsset
import org.junit.Rule
import org.junit.Test
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
class DisplayableAssetTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    private val darkModeVariantUrl = "dark-mode-variant-of-asset"
    private val lightModeVariantUrl = "light-mode-variant-of-asset"

    private val image = DisplayableAsset(
        dark = darkModeVariantUrl,
        light = lightModeVariantUrl,
    )

    @Test
    @Config(qualifiers = "night")
    fun urlResolvesToDarkVariantInDarkMode() {
        expectResolvedComposeUrl(darkModeVariantUrl, image)
    }

    @Test
    @Config(qualifiers = "night")
    fun urlResolvesToLightVariantInDarkModeWhenDarkVariantCannotBeResolved() {
        val lightModeOnly = DisplayableAsset(
            light = lightModeVariantUrl
        )

        expectResolvedComposeUrl(lightModeVariantUrl, lightModeOnly)
    }

    @Test
    @Config(qualifiers = "notnight")
    fun urlResolvesToLightVariantInLightMode() {
        expectResolvedComposeUrl(lightModeVariantUrl, image)
    }

    @Test
    @Config(qualifiers = "notnight")
    fun urlResolvesToDarkVariantInLightModeWhenExplicitlyRequested() {
        expectResolvedComposeUrl(darkModeVariantUrl, image, true)
    }

    private fun expectResolvedComposeUrl(expected: String, image: DisplayableAsset, useDarkMode: Boolean? = null) {
        composeTestRule.setContent {
            val resolvedString = if (useDarkMode != null) {
                image.resolve(useDarkMode)
            } else {
                image.resolve()
            }
            assertEquals(expected, resolvedString)
        }
    }
}
