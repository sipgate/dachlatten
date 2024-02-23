package de.sipgate.dachlatten.primitives.filter

import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test

class PredicateTest {
    private val truePredicate = object : Predicate<Any> {
        override suspend fun invoke(test: Any): Boolean = true
    }

    private val falsePredicate = object : Predicate<Any> {
        override suspend fun invoke(test: Any): Boolean = false
    }

    @Test
    fun truePredicateReturnsTrueResult() = runTest {
        val result = truePredicate(Unit)
        assertTrue(result)
    }

    @Test
    fun falsePredicateReturnsFalseResult() = runTest {
        val result = falsePredicate(Unit)
        assertFalse(result)
    }

    @Test
    fun trueAndTruePredicatesCombineToTrueResult() = runTest {
        val combined = truePredicate and truePredicate
        val result = combined(Unit)

        assertTrue(result)
    }

    @Test
    fun trueAndFalsePredicatesCombineToFalseResult() = runTest {
        val combined = truePredicate and falsePredicate
        val result = combined(Unit)

        assertFalse(result)
    }

    @Test
    fun falseAndTruePredicatesCombineToFalseResult() = runTest {
        val combined = falsePredicate and truePredicate
        val result = combined(Unit)

        assertFalse(result)
    }

    @Test
    fun falseAndfalsePredicatesCombineToFalseResult() = runTest {
        val combined = falsePredicate and falsePredicate
        val result = combined(Unit)

        assertFalse(result)
    }

    @Test
    fun trueOrTruePredicatesCombineToTrueResult() = runTest {
        val combined = truePredicate or truePredicate
        val result = combined(Unit)

        assertTrue(result)
    }

    @Test
    fun trueOrFalsePredicatesCombineToTrueResult() = runTest {
        val combined = truePredicate or falsePredicate
        val result = combined(Unit)

        assertTrue(result)
    }

    @Test
    fun falseOrTruePredicatesCombineToTrueResult() = runTest {
        val combined = falsePredicate or truePredicate
        val result = combined(Unit)

        assertTrue(result)
    }

    @Test
    fun falseOrfalsePredicatesCombineToFalseResult() = runTest {
        val combined = falsePredicate or falsePredicate
        val result = combined(Unit)

        assertFalse(result)
    }

    @Test
    fun notOnTrueResultsInFalse() = runTest {
        val not = not(truePredicate)
        val result = not(Unit)

        assertFalse(result)
    }

    @Test
    fun notOnFalseResultsInTrue() = runTest {
        val not = not(falsePredicate)
        val result = not(Unit)

        assertTrue(result)
    }
}
