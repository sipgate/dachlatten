package de.sipgate.dachlatten.primitives

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotNull
import kotlin.test.assertNull

class StringExtTest {
    @Test
    fun sha256PropertyForRegularStringReturnsTheCorrectValue() {
        assertEquals("9f86d081884c7d659a2feaa0c55ad015a3bf4f1b2b0b822cd15d6c15b0f00a08", "test".sha256)
    }

    @Test
    fun sha256PropertyForUnicodeStringReturnsTheCorrectValue() {
        assertEquals("d7a5c5e0d1d94c32218539e7e47d4ba9c3c7b77d61332fb60d633dde89e473fb", "\uD83E\uDD86".sha256)
    }

    @Test
    fun trimmedMultilineIndentEqualsTheOldLongFormat() {
        // assurance check, to guard against regression
        @Suppress("MaxLineLength")
        val long = "c582a45454fc09fbd35c640d92308cb4b2a23f4b16d25bada01691ad04f3f9fb3e4320e0e84e2cfcd20e33290eabea91d1399"

        val short = """
        c582a45454fc09fbd35c640d92308cb4b2a23f4b16d25bada01691ad04f3f9fb
        3e4320e0e84e2cfcd20e33290eabea91d1399
        """.trimWhitespace()

        assertEquals(long, short)
    }

    @Test
    fun ensureEndsWithSlashAppendsSlashToStringThatDoesntEndInSlash() {
        val input = "some string"

        val expected = "some string/"

        assertEquals(expected, input.ensureEndsWithSlash())
    }

    @Test
    fun ensureEndsWithSlashReturnsItsInputUnchangedWhenItAlreadyEndsInSlash() {
        val input = "some string/"

        val expected = input

        assertEquals(expected, input.ensureEndsWithSlash())
    }

    @Test
    fun nullIfEmptyReturnsNullForEmptyString() {
        val input = ""

        val expected = input.nullIfEmpty()

        assertNull(expected)
    }

    @Test
    fun nullIfEmptyReturnsNonNullForBlankString() {
        val input = "   "

        val expected = input.nullIfEmpty()

        assertNotNull(expected)
    }

    @Test
    fun nullIfEmptyReturnsNonNullForNonEmptyString() {
        val input = "test"

        val expected = input.nullIfEmpty()

        assertNotNull(expected)
    }
}
