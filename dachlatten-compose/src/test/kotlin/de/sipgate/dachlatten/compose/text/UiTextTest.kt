package de.sipgate.dachlatten.compose.text

import android.content.res.Resources
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.core.R
import de.sipgate.dachlatten.text.UiText
import org.junit.Rule
import org.junit.Test
import org.junit.jupiter.api.assertThrows
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config
import java.util.Locale
import kotlin.test.assertEquals

@RunWith(RobolectricTestRunner::class)
class UiTextTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    @Config(qualifiers = "de")
    fun stringResourceResolvesValueWhenCompositionIsRequested() {

        val uiText = UiText.StringResource(R.string.call_notification_answer_action)
        expectResolvedComposeString("Annehmen", uiText)
    }

    @Test
    @Config(qualifiers = "en")
    fun stringSubstitutionWorksFromCompose() {
        val uiText = UiText.MultiLangString(
            mapOf("EN" to "string %s and %s"),
            fallbackResource = null,
            "substitution", "others"
        )
        expectResolvedComposeString("string substitution and others", uiText)
    }

    @Test
    @Config(qualifiers = "en")
    fun exceptionIsThrownWhenTheTranslationCannotBeFound() {
        val uiText = UiText.MultiLangString(mapOf("invalid-key" to "some string"))

        assertThrows<Resources.NotFoundException> {
            composeTestRule.setContent {
                uiText.resolve()
            }
        }
    }

    @Test
    @Config(qualifiers = "en")
    fun fallbackIsUsedIfTranslationCannotBeFound() {
        val uiText = UiText.MultiLangString(
            mapOf("invalid-key" to "some string"),
            fallbackResource = R.string.call_notification_answer_action
        )
        expectResolvedComposeString("Answer", uiText)
    }

    @Test
    @Config(qualifiers = "fr")
    fun multilangResolvesFallbackWhenLanguageCannotBeFound() {
        val uiText = UiText.MultiLangString(mapOf(
            "en" to "String",
            "de" to "Zeichenfolge"
        ))
        expectResolvedComposeString("String", uiText, fallbackLocale = Locale.ENGLISH)
    }

    private fun expectResolvedComposeString(expected: String, uiText: UiText, fallbackLocale: Locale? = null) {
        composeTestRule.setContent {
            val resolvedString = uiText.resolve(fallbackLocale)

            /*
             * This is needed because the Strings we have packaged with Robolectric
             * returns BiDi marks, which causes the String comparison to fail.
             */
            val sanitizedString = resolvedString.filter { it.isLetterOrDigit() || it.isWhitespace() }

            assertEquals(expected, sanitizedString)
        }
    }
}
