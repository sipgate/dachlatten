package de.sipgate.dachlatten.flow

import app.cash.turbine.test
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
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
        val refreshSignal: RefreshSignal = MutableStateFlow(false)

        val output = input
            .shareIn(backgroundScope, SharingStarted.WhileSubscribed())
            .refreshableFlow(refreshSignal)

        output.test {
            val a = awaitItem()
            val b = awaitItem()
            assertEquals(1, a)
            assertEquals(2, b)

            refreshSignal.refresh()

            assertEquals(2, awaitItem())
        }
    }

    @Test
    fun invokingTheRefreshSignalCausesTheLastEmittedItemToBeProcessedAgain() = runTest {
        val refreshSignal: RefreshSignal = MutableStateFlow(false)

        var factor = 1
        val output = input
            .shareIn(backgroundScope, SharingStarted.WhileSubscribed())
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
