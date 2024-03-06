package de.sipgate.dachlatten.android.text

import android.content.res.Resources
import androidx.core.R
import de.sipgate.dachlatten.text.UiText
import java.util.Locale
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

    private val upperCaseLanguageMap = mapOf(
        "EN" to "String",
        "DE" to "Zeichenfolge"
    )

    @Test
    @Config(qualifiers = "de")
    fun multiLangStringResolvesValueWhenRequestedUpperCaseDe() {
        val uiText = UiText.MultiLangString(upperCaseLanguageMap)
        expectResolvedResourceString("Zeichenfolge", uiText)
    }

    @Test
    @Config(qualifiers = "en")
    fun multiLangStringResolvesValueWhenRequestedUpperCaseEn() {
        val uiText = UiText.MultiLangString(upperCaseLanguageMap)
        expectResolvedResourceString("String", uiText)
    }

    private val lowerCaseLanguageMap = mapOf(
        "en" to "String",
        "de" to "Zeichenfolge"
    )

    @Test
    @Config(qualifiers = "de")
    fun multiLangStringResolvesValueWhenRequestedLowerCaseDe() {
        val uiText = UiText.MultiLangString(lowerCaseLanguageMap)
        expectResolvedResourceString("Zeichenfolge", uiText)
    }

    @Test
    @Config(qualifiers = "en")
    fun multiLangStringResolvesValueWhenRequestedLowerCaseEn() {
        val uiText = UiText.MultiLangString(lowerCaseLanguageMap)
        expectResolvedResourceString("String", uiText)
    }

    @Test
    @Config(qualifiers = "fr")
    fun multilangResolvesFallbackWhenLanguageCannotBeFound() {
        val uiText = UiText.MultiLangString(lowerCaseLanguageMap)
        expectResolvedResourceString("String", uiText, fallbackLocale = Locale.ENGLISH)
    }

    @Test
    @Config(qualifiers = "en")
    fun stringSubstitutionWorks() {
        val uiText = UiText.MultiLangString(
            mapOf("EN" to "string %s"),
            fallbackResource = null,
            "substitution"
        )
        expectResolvedResourceString("string substitution", uiText)
    }

    @Test
    @Config(qualifiers = "en")
    fun stringSubstitutionWorksForMultipleArguments() {
        val uiText = UiText.MultiLangString(
            mapOf("EN" to "string %s and %s"),
            fallbackResource = null,
            "substitution", "others"
        )
        expectResolvedResourceString("string substitution and others", uiText)
    }

    @Test
    @Config(qualifiers = "en")
    fun exceptionIsThrownWhenTheTranslationCannotBeFound() {
        val uiText = UiText.MultiLangString(mapOf("invalid-key" to "some string"))

        assertThrows<Resources.NotFoundException> {
            expectResolvedResourceString("bla", uiText)
        }
    }

    private fun expectResolvedResourceString(expected: String, uiText: UiText, fallbackLocale: Locale? = null) {
        assertEquals(expected, uiText.resolve(context.resources, fallbackLocale))
    }
}
