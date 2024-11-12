package de.sipgate.dachlatten.primitives.predicates

import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class PredicateTest {
    private val truePredicate: Predicate<Unit> = { true }

    private val falsePredicate:  Predicate<Unit> = { false }

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
    fun falseOrFalsePredicatesCombineToFalseResult() = runTest {
        val combined = falsePredicate or falsePredicate
        val result = combined(Unit)

        assertFalse(result)
    }

    @Test
    fun notOnTrueResultsInFalse() = runTest {
        val not = !truePredicate
        val result = not(Unit)

        assertFalse(result)
    }

    @Test
    fun notOnFalseResultsInTrue() = runTest {
        val not = !falsePredicate
        val result = not(Unit)

        assertTrue(result)
    }
}
