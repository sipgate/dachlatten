package de.sipgate.dachlatten.compose.text

import android.content.res.Resources
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.core.R
import org.junit.Rule
import org.junit.Test
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.assertThrows
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.RuntimeEnvironment
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
class UiTextTest {

    private val context = RuntimeEnvironment.getApplication().applicationContext

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    @Config(qualifiers = "en")
    fun dynamicStringPassesStringsVerbatim() {
        val uiText = UiText.DynamicString("asdf")
        expectResolvedResourceString("asdf", uiText)
    }

    @Test
    @Config(qualifiers = "de")
    fun stringResourceResolvesValueWhenRequestedDe() {
        val uiText = UiText.StringResource(R.string.call_notification_answer_action)
        expectResolvedResourceString("Annehmen", uiText)
    }

    @Test
    @Config(qualifiers = "en-rUS")
    fun stringResourceResolvesValueWhenRequestedEn() {
        val uiText = UiText.StringResource(R.string.call_notification_answer_action)
        expectResolvedResourceString("Answer", uiText)
    }

    @Test
    @Config(qualifiers = "de")
    fun stringResourceResolvesValueWhenCompositionIsRequested() {
        val uiText = UiText.StringResource(R.string.call_notification_answer_action)
        expectResolvedComposeString("Annehmen", uiText)
    }

    private val languageMap = mapOf(
        "EN" to "String",
        "DE" to "Zeichenfolge"
    )

    @Test
    @Config(qualifiers = "de")
    fun multiLangStringResolvesValueWhenRequestedDe() {
        val uiText = UiText.MultiLangString(languageMap)
        expectResolvedResourceString("Zeichenfolge", uiText)
    }

    @Test
    @Config(qualifiers = "en")
    fun multiLangStringResolvesValueWhenRequestedEn() {
        val uiText = UiText.MultiLangString(languageMap)
        expectResolvedResourceString("String", uiText)
    }

    @Test
    @Config(qualifiers = "en")
    fun stringSubstitutionWorks() {
        val uiText = UiText.MultiLangString(mapOf("EN" to "string %s"),
            fallbackResource = null,
            "substitution")
        expectResolvedResourceString("string substitution", uiText)
    }

    @Test
    @Config(qualifiers = "en")
    fun stringSubstitutionWorksForMultipleArguments() {
        val uiText = UiText.MultiLangString(mapOf("EN" to "string %s and %s"),
            fallbackResource = null,
            "substitution", "others")
        expectResolvedResourceString("string substitution and others", uiText)
    }

    @Test
    @Config(qualifiers = "en")
    fun stringSubstitutionWorksFromCompose() {
        val uiText = UiText.MultiLangString(mapOf("EN" to "string %s and %s"),
            fallbackResource = null,
            "substitution", "others")
        expectResolvedComposeString("string substitution and others", uiText)
    }

    @Test
    @Config(qualifiers = "en")
    fun exceptionIsThrownWhenTheTranslationCannotBeFound() {
        val uiText = UiText.MultiLangString(mapOf("invalid-key" to "some string"))

        assertThrows<Resources.NotFoundException> {
            composeTestRule.setContent {
                uiText.asString()
            }
        }
    }

    @Test
    @Config(qualifiers = "en")
    fun fallbackIsUsedIfTranslationCannotBeFound() {
        val uiText = UiText.MultiLangString(mapOf("invalid-key" to "some string"),
            fallbackResource = R.string.call_notification_answer_action)
        expectResolvedComposeString("Answer", uiText)
    }

    @Test
    @Config(qualifiers = "en")
    fun multiLangKeyInWrongCaseWill() {
        val uiText = UiText.MultiLangString(mapOf("en" to "some string"),
            fallbackResource = R.string.call_notification_answer_action)
        expectResolvedComposeString("Answer", uiText)
    }

    private fun expectResolvedResourceString(expected: String, uiText: UiText) {
        assertEquals(expected, uiText.asString(context.resources))
    }

    private fun expectResolvedComposeString(expected: String, uiText: UiText) {
        composeTestRule.setContent {
            val resolvedString = uiText.asString()

            /*
             * This is needed because the Strings we have packaged with Robolectric
             * returns BiDi marks, which causes the String comparison to fail.
             */
            val sanitizedString = resolvedString.filter { it.isLetterOrDigit() || it.isWhitespace() }

            assertEquals(expected, sanitizedString)
        }
    }
}
