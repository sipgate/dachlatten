package de.sipgate.dachlatten.primitives

import de.sipgate.dachlatten.primitives.EnumExtTest.SomeValues.A
import de.sipgate.dachlatten.primitives.EnumExtTest.SomeValues.B
import de.sipgate.dachlatten.primitives.EnumExtTest.SomeValues.C
import kotlin.test.Test
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class EnumExtTest {
    enum class SomeValues {
        A,
        B,
        C,
    }

    @Test
    fun testIsAnyOfSelf() {
        assertTrue(A.isAnyOf(A))
    }

    @Test
    fun testIsAnyOfWithSubset() {
        assertTrue(A.isAnyOf(A, B))
    }

    @Test
    fun testIsAnyOfFullEnum() {
        assertTrue(A.isAnyOf(*SomeValues.entries.toTypedArray()))
    }

    @Test
    fun testIsAnyOfNotContained() {
        assertFalse(A.isAnyOf(B, C))
    }

    @Test
    fun testIsNoneOfSelfIsFalse() {
        assertFalse(A.isNoneOf(A))
    }

    @Test
    fun testIsNoneOfOther() {
        assertTrue(A.isNoneOf(B))
    }

    @Test
    fun testIsNoneOfNotContained() {
        assertTrue(A.isNoneOf(B, C))
    }
}
