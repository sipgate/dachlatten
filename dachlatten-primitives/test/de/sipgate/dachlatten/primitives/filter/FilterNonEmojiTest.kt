package de.sipgate.dachlatten.primitives.filter

import kotlin.test.Test
import kotlin.test.assertEquals


class FilterNonEmojiTest {
    @Test
    fun testBasicEmojisAreFiltered() {
        assertEquals("aabb", "aa🙂bb".withoutEmojisAndOtherSymbols())
    }

    @Test
    fun testDigitsArePreserved() {
        assertEquals("aa12bb", "aa12bb".withoutEmojisAndOtherSymbols())
    }

    @Test
    fun testUmlautsArePreserved() {
        assertEquals("aaÜüÖöÄäbb", "aaÜüÖöÄäbb".withoutEmojisAndOtherSymbols())
    }
}
