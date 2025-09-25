package de.sipgate.dachlatten.android.text

import android.content.Context
import android.content.res.Resources
import de.sipgate.dachlatten.android.R
import de.sipgate.dachlatten.text.UiText
import de.sipgate.dachlatten.android.setLocale
import java.util.Locale
import kotlin.test.assertEquals
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

class UiTextTest {

    @Test
    fun fixedStringPassesStringsVerbatim() {
        setLocale("en")

        val uiText = UiText.DynamicString("asdf")
        expectResolvedResourceString("asdf", uiText)
    }

    @Test
    @OptIn(ExperimentalUuidApi::class)
    fun dynamicStringPassesStringsVerbatim() {
        setLocale("en")

        val duringRuntime by lazy { Uuid.random().toString() }
        val uiText = UiText.DynamicString { duringRuntime }
        expectResolvedResourceString(duringRuntime, uiText)
    }

    @Test
    fun stringResourceResolvesValueWhenRequestedDe() {
        setLocale("de")

        val uiText = UiText.StringResource(R.string.call_notification_answer_action)
        expectResolvedResourceString("Annehmen", uiText)
    }

    @Test
    fun stringResourceResolvesValueWhenRequestedEn() {
        setLocale("en-rUS")

        val uiText = UiText.StringResource(R.string.call_notification_answer_action)
        expectResolvedResourceString("Answer", uiText)
    }

    private val upperCaseLanguageMap = mapOf(
        "EN" to "String",
        "DE" to "Zeichenfolge"
    )

    @Test
    fun multiLangStringResolvesValueWhenRequestedUpperCaseDe() {
        setLocale("de")

        val uiText = UiText.MultiLangString(upperCaseLanguageMap)
        expectResolvedResourceString("Zeichenfolge", uiText)
    }

    @Test
    fun multiLangStringResolvesValueWhenRequestedUpperCaseEn() {
        setLocale("en")

        val uiText = UiText.MultiLangString(upperCaseLanguageMap)
        expectResolvedResourceString("String", uiText)
    }

    private val lowerCaseLanguageMap = mapOf(
        "en" to "String",
        "de" to "Zeichenfolge"
    )

    @Test
    fun multiLangStringResolvesValueWhenRequestedLowerCaseDe() {
        setLocale("de")

        val uiText = UiText.MultiLangString(lowerCaseLanguageMap)
        expectResolvedResourceString("Zeichenfolge", uiText)
    }

    @Test
    fun multiLangStringResolvesValueWhenRequestedLowerCaseEn() {
        setLocale("en")

        val uiText = UiText.MultiLangString(lowerCaseLanguageMap)
        expectResolvedResourceString("String", uiText)
    }

    @Test
    fun multilangResolvesFallbackWhenLanguageCannotBeFound() {
        setLocale("fr")

        val uiText = UiText.MultiLangString(lowerCaseLanguageMap)
        expectResolvedResourceString("String", uiText, fallbackLocale = Locale.ENGLISH)
    }

    @Test
    fun stringSubstitutionWorks() {
        setLocale("en")

        val uiText = UiText.MultiLangString(
            mapOf("EN" to "string %s"),
            fallbackResource = null,
            "substitution"
        )
        expectResolvedResourceString("string substitution", uiText)
    }

    @Test
    fun stringSubstitutionWorksForMultipleArguments() {
        setLocale("en")

        val uiText = UiText.MultiLangString(
            mapOf("EN" to "string %s and %s"),
            fallbackResource = null,
            "substitution", "others"
        )
        expectResolvedResourceString("string substitution and others", uiText)
    }

    @Test
    fun exceptionIsThrownWhenTheTranslationCannotBeFound() {
        setLocale("en")

        val uiText = UiText.MultiLangString(mapOf("invalid-key" to "some string"))

        assertFailsWith<Resources.NotFoundException> {
            expectResolvedResourceString("bla", uiText)
        }
    }

    private fun expectResolvedResourceString(
        expected: String,
        uiText: UiText,
        fallbackLocale: Locale? = null
    ) {
        assertEquals(expected, uiText.resolve(context.resources, fallbackLocale))
    }
}
