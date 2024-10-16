package de.sipgate.dachlatten.primitives

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class ScreenReaderHelperTest {
    @Test
    fun simpleHelloTest() {
        assertEquals("H e l l o", "Hello".readAsSingles())
    }

    @Test
    fun simpleNumberTest() {
        assertEquals("2 2 9 2 2", "22922".readAsSingles())
    }
}
