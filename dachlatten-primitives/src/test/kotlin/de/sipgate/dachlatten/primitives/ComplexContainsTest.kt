package de.sipgate.dachlatten.primitives

import de.sipgate.dachlatten.primitives.predicates.Predicate
import de.sipgate.dachlatten.primitives.predicates.not
import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class ComplexContainsTest {

    val list = listOf("a1entry", "b2entry", "otherEntry")

    @Test
    fun testSimpleContains() = runTest {
        val result = list.contains<String> { it == "a1entry" }
        assertTrue(result)
    }

    @Test
    fun testSimpleContainsNegative() = runTest {
        val result = list.contains<String> { it == "does-not-exist" }
        assertFalse(result)
    }

    @Test
    fun testComplexContains() = runTest {
        val result = list.contains<String> { it.all { it.isLetter() } }
        assertTrue(result)
    }

    @Test
    fun testComplexContainsNegative() = runTest {
        val result = list.contains<String> { it.all { it.isWhitespace() } }
        assertFalse(result)
    }

    @Test
    fun testComplexContainsInvert() = runTest {
        val complexPredicate: Predicate<String> = { it.all { it.isWhitespace() } }
        val invertedPredicate = !complexPredicate
        val result = list.contains<String>(invertedPredicate)
        assertTrue(result)
    }
}
