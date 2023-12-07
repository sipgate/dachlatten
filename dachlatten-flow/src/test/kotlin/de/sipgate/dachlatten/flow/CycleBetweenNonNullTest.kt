package de.sipgate.dachlatten.flow

import app.cash.turbine.test
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.consumeAsFlow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.onEmpty
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.test.advanceTimeBy
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import kotlin.time.Duration.Companion.seconds

class CycleBetweenNonNullTest {
    @Test
    fun cyclerReturnsAlwaysAWhenBIsNull() = runTest {
        cycleBetweenNonNull(ticker.toFlow(), flowA, nullFlow).test {
            assertEquals(contentA, awaitItem())

            tick()
            assertEquals(contentA, awaitItem())

            tick()
            assertEquals(contentA, awaitItem())
        }
    }

    @Test
    fun cyclerReturnsAlwaysBWhenAIsNull() = runTest {
        cycleBetweenNonNull(ticker.toFlow(), nullFlow, flowB).test {
            assertEquals(contentB, awaitItem())

            tick()
            assertEquals(contentB, awaitItem())

            tick()
            assertEquals(contentB, awaitItem())
        }
    }

    @Test
    fun cyclerReturnsNullWhenBothAAndBAreNull() = runTest {
        cycleBetweenNonNull(ticker.toFlow(), nullFlow, nullFlow).test {
            Assertions.assertNull(awaitItem())

            tick()
            Assertions.assertNull(awaitItem())
        }
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun cycleBetweenWithDurationWorks() = runTest {
        cycleBetweenNonNull(5.seconds, flowA, flowB).test {
            assertEquals(contentA, awaitItem())

            expectNoEvents()
            advanceTimeBy(5.seconds)
            assertEquals(contentB, awaitItem())

            expectNoEvents()
            advanceTimeBy(5.seconds)
            assertEquals(contentA, awaitItem())
        }
    }

    @Test
    fun cycleBetweenWithDurationAndNullWorks() = runTest {
        cycleBetweenNonNull(ticker.toFlow(), flowA, nullFlow).test {
            assertEquals(contentA, awaitItem())

            tick()
            assertEquals(contentA, awaitItem())

            tick()
            assertEquals(contentA, awaitItem())
        }
    }

    private val contentA = "contentA"
    private val flowA = flowOf(contentA)

    private val contentB = "contentB"
    private val flowB = flowOf(contentB)

    private val nullFlow = flowOf<Unit?>(null)

    private val ticker = Channel<Unit?>()

    private suspend fun tick() {
        ticker.send(null)
    }

    private fun <T> Channel<T>.toFlow() = receiveAsFlow().onEmpty<T?> { emit(null) }
}
