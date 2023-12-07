package de.sipgate.dachlatten.flow

import app.cash.turbine.test
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test

class BooleanFlowTest {

    @Nested
    inner class OperatorApi {
        @Test
        fun eitherReturnsTrueIfAnyFlowEmitsTrue() = runTest {
            val falseFlow = flowOf(false)
            val trueFlow = flowOf(true)

            val result = either(falseFlow, trueFlow)
            assertFinalValueEquals(true, result)
        }

        @Test
        fun eitherReturnsFalseIfBothFlowsEmitFalse() = runTest {
            val falseFlow = flowOf(false)
            val anotherFalseFlow = flowOf(false)

            val result = either(falseFlow, anotherFalseFlow)
            assertFinalValueEquals(false, result)
        }

        @Test
        fun eitherReturnsTrueIfAllFlowsEmitTrue() = runTest {
            val trueFlow = flowOf(true)
            val anotherTrueFlow = flowOf(true)

            val result = either(trueFlow, anotherTrueFlow)
            assertFinalValueEquals(true, result)
        }

        @Test
        fun bothReturnsTrueIfAllFlowsEmitTrue() = runTest {
            val trueFlow = flowOf(true)
            val anotherTrueFlow = flowOf(true)

            val result = both(trueFlow, anotherTrueFlow)
            assertFinalValueEquals(true, result)
        }

        @Test
        fun bothReturnsFalseIfAnyFlowEmitsFalse() = runTest {
            val trueFlow = flowOf(true)
            val falseFlow = flowOf(false)

            val result = both(trueFlow, falseFlow)
            assertFinalValueEquals(false, result)
        }

        @Test
        fun bothReturnsFalseIfBothFlowsEmitFalse() = runTest {
            val falseFlow = flowOf(false)
            val anotherFalseFlow = flowOf(false)

            val result = both(falseFlow, anotherFalseFlow)
            assertFinalValueEquals(false, result)
        }
    }

    @Nested
    inner class InfixApi {
        @Test
        fun orReturnsTrueIfAnyFlowEmitsTrue() = runTest {
            val falseFlow = flowOf(false)
            val trueFlow = flowOf(true)

            val result = falseFlow or trueFlow
            assertFinalValueEquals(true, result)
        }

        @Test
        fun orReturnsFalseIfBothFlowsEmitFalse() = runTest {
            val falseFlow = flowOf(false)
            val anotherFalseFlow = flowOf(false)

            val result = falseFlow or anotherFalseFlow
            assertFinalValueEquals(false, result)
        }

        @Test
        fun orReturnsTrueIfAllFlowsEmitTrue() = runTest {
            val trueFlow = flowOf(true)
            val anotherTrueFlow = flowOf(true)

            val result = trueFlow or anotherTrueFlow
            assertFinalValueEquals(true, result)
        }

        @Test
        fun andReturnsTrueIfAllFlowsEmitTrue() = runTest {
            val trueFlow = flowOf(true)
            val anotherTrueFlow = flowOf(true)

            val result = trueFlow and anotherTrueFlow
            assertFinalValueEquals(true, result)
        }

        @Test
        fun andReturnsFalseIfAnyFlowEmitsFalse() = runTest {
            val trueFlow = flowOf(true)
            val falseFlow = flowOf(false)

            val result = trueFlow and falseFlow
            assertFinalValueEquals(false, result)
        }

        @Test
        fun andReturnsFalseIfBothFlowsEmitFalse() = runTest {
            val falseFlow = flowOf(false)
            val anotherFalseFlow = flowOf(false)

            val result = falseFlow and anotherFalseFlow
            assertFinalValueEquals(false, result)
        }
    }
}

private suspend fun <T> assertFinalValueEquals(expected: T, actual: Flow<T>) = actual.test {
    assertEquals(expected, awaitItem())
    awaitComplete()
}
