package de.sipgate.dachlatten.primitives

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class StringExtTest {
    @Test
    fun sha256PropertyForRegularStringReturnsTheCorrectValue() {
        assertEquals("9f86d081884c7d659a2feaa0c55ad015a3bf4f1b2b0b822cd15d6c15b0f00a08", "test".sha256)
    }

    @Test
    fun sha256PropertyForUnicodeStringReturnsTheCorrectValue() {
        assertEquals("d7a5c5e0d1d94c32218539e7e47d4ba9c3c7b77d61332fb60d633dde89e473fb", "\uD83E\uDD86".sha256)
    }
}
