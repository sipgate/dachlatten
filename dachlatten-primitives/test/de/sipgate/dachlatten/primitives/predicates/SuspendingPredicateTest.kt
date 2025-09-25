package de.sipgate.dachlatten.primitives.predicates

import kotlinx.coroutines.delay
import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.time.Duration.Companion.milliseconds

class SuspendingPredicateTest {
    @Test
    fun suspendingPredicateTest() = runTest {
        val suspendingFilter = object : SuspendingPredicate<Unit> {
            override suspend fun invokeSuspending(test: Unit): Boolean {
                delay(15.milliseconds)
                return true
            }
        }

        val result = listOf(Unit).filter(suspendingFilter)
        assertEquals(1, result.size)
    }
}
