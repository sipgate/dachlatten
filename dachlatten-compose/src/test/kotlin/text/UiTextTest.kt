package de.sipgate.dachlatten.compose.text

import androidx.compose.ui.test.junit4.createComposeRule
import androidx.core.R
import org.junit.Rule
import org.junit.Test
import org.junit.jupiter.api.Assertions.assertEquals
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
        "en" to "String",
        "de" to "Zeichenfolge"
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

    private fun expectResolvedResourceString(expected: String, uiText: UiText) {
        assertEquals(expected, uiText.asString(context.resources))
    }

    private fun expectResolvedComposeString(expected: String, uiText: UiText) {
        composeTestRule.setContent {
            val resolvedString = uiText.asString()
            assertEquals(expected, resolvedString)
        }
    }
}
