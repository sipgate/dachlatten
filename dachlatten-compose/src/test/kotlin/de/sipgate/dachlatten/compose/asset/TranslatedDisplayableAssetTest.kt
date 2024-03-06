package de.sipgate.dachlatten.compose.asset

import androidx.compose.ui.test.junit4.createComposeRule
import de.sipgate.dachlatten.asset.DisplayableAsset
import de.sipgate.dachlatten.asset.TranslatedDisplayableAsset
import java.util.Locale
import org.junit.Rule
import org.junit.Test
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
class TranslatedDisplayableAssetTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    private val englishDarkModeVariantUrl = "dark-mode-variant-of-asset-en"
    private val germanDarkModeVariantUrl = "dark-mode-variant-of-asset-de"
    private val englishLightModeVariantUrl = "light-mode-variant-of-asset-en"
    private val germanLightModeVariantUrl = "light-mode-variant-of-asset-de"

    private val translatedImage: TranslatedDisplayableAsset = mapOf(
        "en" to DisplayableAsset(
            dark = englishDarkModeVariantUrl,
            light = englishLightModeVariantUrl,
        ),
        "de" to DisplayableAsset(
            dark = germanDarkModeVariantUrl,
            light = germanLightModeVariantUrl,
        )
    )

    private val englishLightOnly = mapOf(
        "en" to DisplayableAsset(
            light = englishLightModeVariantUrl,
        )
    )

    @Test
    @Config(qualifiers = "de-night")
    fun urlResolvesToGermanDarkVariantInDarkModeAndDeviceSetToGerman() {
        expectResolvedComposeUrl(germanDarkModeVariantUrl, translatedImage)
    }

    @Test
    @Config(qualifiers = "en-night")
    fun urlResolvesToEnglishDarkVariantInDarkModeAndDeviceSetToEnglish() {
        expectResolvedComposeUrl(englishDarkModeVariantUrl, translatedImage)
    }

    @Test
    @Config(qualifiers = "en-notnight")
    fun urlResolvesToEnglishLightVariantInLightModeAndDeviceSetToEnglish() {
        expectResolvedComposeUrl(englishLightModeVariantUrl, translatedImage)
    }

    @Test
    @Config(qualifiers = "en-notnight")
    fun urlResolvesToEnglishDarkVariantInLightModeAndDeviceSetToEnglishAndDarkModeExplicitlyRequested() {
        expectResolvedComposeUrl(englishDarkModeVariantUrl, translatedImage, useDarkMode = true)
    }

    @Test
    @Config(qualifiers = "en-night")
    fun urlResolvesToEnglishLightVariantInDarkModeAndDeviceSetToEnglishWhenDarkModeResourcesAreNotAvailable() {
        expectResolvedComposeUrl(englishLightModeVariantUrl, englishLightOnly)
    }

    @Test
    @Config(qualifiers = "en-night")
    fun urlResolvesToEnglishLightVariantInDarkModeAndDeviceSetToEnglishWhenDarkModeResourcesAreNotAvailableAndDarkModeExplicitlyRequested() {
        expectResolvedComposeUrl(englishLightModeVariantUrl, englishLightOnly, useDarkMode = true)
    }

    @Test
    @Config(qualifiers = "fr-notnight")
    fun urlResolvesToGermanLightVariantInLightModeAndDeviceSetToEnglishWhenTheResourcesAreNotAvailable() {
        val germanLightVariantOnly = mapOf(
            "de" to DisplayableAsset(
                light = germanLightModeVariantUrl
            )
        )
        expectResolvedComposeUrl(germanLightModeVariantUrl, germanLightVariantOnly, fallbackLocale = Locale.GERMAN)
    }

    @Test
    @Config(qualifiers = "fr-night")
    fun urlResolvesToGermanLightVariantInDarkModeAndDeviceSetToEnglishWhenTheResourcesAreNotAvailable() {
        val germanLightVariantOnly = mapOf(
            "de" to DisplayableAsset(
                light = germanLightModeVariantUrl
            )
        )
        expectResolvedComposeUrl(germanLightModeVariantUrl, germanLightVariantOnly, fallbackLocale = Locale.GERMAN)
    }

    private fun expectResolvedComposeUrl(expected: String, image: TranslatedDisplayableAsset,
                                         fallbackLocale: Locale? = null,
                                         useDarkMode: Boolean? = null) {
        composeTestRule.setContent {
            val resolvedString = if (useDarkMode != null) {
                image.resolve(useDarkMode = useDarkMode, fallbackLocale = fallbackLocale)
            } else {
                image.resolve(fallbackLocale = fallbackLocale)
            }
            assertEquals(expected, resolvedString)
        }
    }
}
