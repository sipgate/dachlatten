package de.sipgate.dachlatten.flow

import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import kotlin.test.Test

class BooleanFlowInfixApiTest {

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
