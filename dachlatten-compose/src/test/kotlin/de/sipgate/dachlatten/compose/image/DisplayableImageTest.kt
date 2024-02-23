package de.sipgate.dachlatten.compose.image

import androidx.compose.ui.test.junit4.createComposeRule
import de.sipgate.dachlatten.image.DisplayableImage
import org.junit.Rule
import org.junit.Test
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
class DisplayableImageTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    private val darkModeVariantUrl = "dark-mode-variant-of-image"
    private val lightModeVariantUrl = "light-mode-variant-of-image"

    private val image = DisplayableImage(
        dark = darkModeVariantUrl,
        light = lightModeVariantUrl,
    )

    @Test
    @Config(qualifiers = "night")
    fun urlResolvesToDarkVariantInDarkMode() {
        expectResolvedComposeUrl(darkModeVariantUrl, image)
    }

    @Test
    @Config(qualifiers = "notnight")
    fun urlResolvesToLightVariantInLightMode() {
        expectResolvedComposeUrl(lightModeVariantUrl, image)
    }

    private fun expectResolvedComposeUrl(expected: String, image: DisplayableImage) {
        composeTestRule.setContent {
            val resolvedString = image.getThemeDependingUri()
            assertEquals(expected, resolvedString)
        }
    }
}
