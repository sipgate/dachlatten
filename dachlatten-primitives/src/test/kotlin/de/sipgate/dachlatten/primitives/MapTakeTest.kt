package de.sipgate.dachlatten.primitives

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import kotlin.test.assertFailsWith

class MapTakeTest {
    @Test
    fun takePassesTheEmptyListBackWithoutCrashing() {
        val map = emptyMap<String, String>()
        val result = map.take(1)
        assertEquals(map, result)
    }

    @Test
    fun takePassesTheEntireListBackWhenTakeCountIsBiggerThanMap() {
        val map = mapOf(1 to "one", 2 to "two")
        val result = map.take(5)
        assertEquals(map, result)
    }

    @Test
    fun takePassesTheSlicedMapBackIfTakeCountIsSmallerThanMap() {
        val map = mapOf(1 to "one", 2 to "two", 3 to "three")
        val result = map.take(2)
        assertEquals(2, result.size)
        assertEquals("one", result.values.first())
        assertEquals("two", result.values.last())
    }

    @Test
    fun passesTheIllegalArgumentExceptionWhenTakeCountIsNegative() {
        val map = mapOf(1 to "one", 2 to "two")
        assertFailsWith<IllegalArgumentException> { map.take(-1) }
    }

    @Test
    fun returnsEmptyListWhenTakeCountIsZero() {
        val map = mapOf(1 to "one", 2 to "two")
        val result = map.take(0)
        assertEquals(emptyMap<Int, String>(), result)
    }
}
