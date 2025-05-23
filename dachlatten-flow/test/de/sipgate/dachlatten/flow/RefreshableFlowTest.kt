package de.sipgate.dachlatten.flow

import app.cash.turbine.test
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.shareIn
import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.test.assertEquals

class RefreshableFlowTest {

    private val input = flow {
        emit(1)
        emit(2)
    }

    @Test
    fun invokingTheRefreshSignalCausesTheLastEmittedItemToBeEmittedAgain() = runTest {
        val refreshSignal = createRefreshSignal()

        input.shareIn(backgroundScope, WhileSubscribed()).refreshableFlow(refreshSignal).test {
            val a = awaitItem()
            val b = awaitItem()
            assertEquals(1, a)
            assertEquals(2, b)

            // Calling `refresh()` on external RefreshSignal
            refreshSignal.refresh()

            assertEquals(2, awaitItem())
        }
    }

    @Test
    fun invokingTheRefreshMethodCausesTheLastEmittedItemToBeEmittedAgain() = runTest {
        val refreshableFlow = input.shareIn(backgroundScope, WhileSubscribed())
            .refreshableFlow()

        refreshableFlow.test {
            val a = awaitItem()
            val b = awaitItem()
            assertEquals(1, a)
            assertEquals(2, b)

            // Calling `refresh()` on the flow itself
            refreshableFlow.refresh()

            assertEquals(2, awaitItem())
        }
    }

    @Test
    fun invokingTheRefreshSignalCausesTheLastEmittedItemToBeProcessedAgain() = runTest {
        val refreshSignal = createRefreshSignal()

        var factor = 1
        val output = input
            .shareIn(backgroundScope, WhileSubscribed())
            .refreshableFlow(refreshSignal)
            .map { it * factor }

        output.test {
            val a = awaitItem()
            val b = awaitItem()
            assertEquals(1, a)
            assertEquals(2, b)

            factor = 5
            refreshSignal.refresh()

            assertEquals(10, awaitItem())
        }
    }
}
