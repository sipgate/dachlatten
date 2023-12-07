package de.sipgate.dachlatten.flow

import app.cash.turbine.test
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.consumeAsFlow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.onEmpty
import kotlinx.coroutines.test.advanceTimeBy
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import kotlin.time.Duration.Companion.seconds

class CycleBetweenTest {
    @Test
    fun cycleBetweenSwitchesTheFlowBeingEmitted() = runTest {
        cycleBetween(ticker.toFlow(), flowA, flowB).test {
            assertEquals(contentA, awaitItem())

            tick()
            assertEquals(contentB, awaitItem())

            tick()
            assertEquals(contentA, awaitItem())
        }
    }

    @Test
    fun cycleBetweenWorksWithNullValues() = runTest {
        cycleBetween(ticker.toFlow(), flowA, nullFlow).test {
            assertEquals(contentA, awaitItem())

            tick()
            assertEquals(null, awaitItem())

            tick()
            assertEquals(contentA, awaitItem())
        }
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun cycleBetweenWithDurationWorks() = runTest {
        cycleBetween(5.seconds, flowA, flowB).test {
            assertEquals(contentA, awaitItem())

            expectNoEvents()
            advanceTimeBy(5.seconds)
            assertEquals(contentB, awaitItem())

            expectNoEvents()
            advanceTimeBy(5.seconds)
            assertEquals(contentA, awaitItem())
        }
    }

    private val contentA = "contentA"
    private val flowA = flowOf(contentA)

    private val contentB = "contentB"
    private val flowB = flowOf(contentB)

    private val nullFlow = flowOf<Unit?>(null)

    private val ticker = Channel<String?>()

    private suspend fun tick() {
        ticker.send(null)
    }

    private fun <T> Channel<T>.toFlow() = consumeAsFlow().onEmpty<T?> { emit(null) }
}
