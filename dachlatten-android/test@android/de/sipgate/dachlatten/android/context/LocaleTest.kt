package de.sipgate.dachlatten.android.context

import android.content.Context
import android.support.test.InstrumentationRegistry
import de.sipgate.dachlatten.android.setLocale
import java.lang.instrument.Instrumentation
import kotlin.test.assertEquals
import kotlin.test.Test

class LocaleTest {

    private val context = InstrumentationRegistry.getContext()

    @Test
    fun testDefaultLanguageIsUsedWhenEmptySet() {
        setLocale("")

        assertEquals(DEFAULT_LANGUAGE, context.language)
    }

    @Test
    fun testGivenLanguageIsUsedWhenSpecified() {
        setLocale("de")

        assertEquals("de", context.language)
    }

    @Test
    fun testLanguageIsUsedWhenSpecifiedComplex() {
        setLocale("en", "rUS")

        assertEquals("en", context.language)
    }
}
