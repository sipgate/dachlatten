package de.sipgate.dachlatten.android.context

import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.RuntimeEnvironment
import org.robolectric.annotation.Config
import kotlin.test.assertEquals

@RunWith(RobolectricTestRunner::class)
class LocaleTest {

    private val context = RuntimeEnvironment.getApplication().applicationContext

    @Test
    fun testDefaultLanguageIsUsedWhenNothingSet() {
        assertEquals(DEFAULT_LANGUAGE, context.language)
    }

    @Test
    @Config(qualifiers = "")
    fun testDefaultLanguageIsUsedWhenEmptySet() {
        assertEquals(DEFAULT_LANGUAGE, context.language)
    }

    @Test
    @Config(qualifiers = "de")
    fun testGivenLanguageIsUsedWhenSpecified() {
        assertEquals("de", context.language)
    }

    @Test
    @Config(qualifiers = "en-rUS")
    fun testLanguageIsUsedWhenSpecifiedComplex() {
        assertEquals("en", context.language)
    }
}
