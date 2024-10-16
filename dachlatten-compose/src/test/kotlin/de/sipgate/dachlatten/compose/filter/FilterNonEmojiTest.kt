package de.sipgate.dachlatten.compose.filter

import androidx.compose.ui.text.input.TextFieldValue
import kotlin.test.Test
import kotlin.test.assertEquals

class FilterNonEmojiTest {
    @Test
    fun testBasicEmojisAreFiltered() {
        assertEquals("aabb", "aa🙂bb".wrapUnwrap())
    }

    @Test
    fun testDigitsArePreserved() {
        assertEquals("aa12bb", "aa12bb".wrapUnwrap())
    }

    @Test
    fun testUmlautsArePreserved() {
        assertEquals("aaÜüÖöÄäbb", "aaÜüÖöÄäbb".wrapUnwrap())
    }

    private fun String.wrapUnwrap() =
        TextFieldValue(this)
            .withoutEmojisAndOtherSymbols()
            .text
}
