package de.sipgate.dachlatten.flow

import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import kotlin.test.Test

class BooleanFlowOperatorApiTest {

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
