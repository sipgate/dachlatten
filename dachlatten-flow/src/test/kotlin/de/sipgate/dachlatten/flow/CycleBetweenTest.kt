package de.sipgate.dachlatten.flow

import app.cash.turbine.test
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.consumeAsFlow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.onEmpty
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import kotlin.time.Duration
import kotlin.time.Duration.Companion.seconds
import kotlin.time.ExperimentalTime
import kotlin.time.TimedValue
import kotlin.time.measureTimedValue

class CycleBetweenTest {
    @Test
    fun cycleBetweenSwitchesTheFlowBeingEmitted() = runTest {
        cycleBetween(ticker.toFlow(), flowA, flowB).test {
            tick()
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
            tick()
            assertEquals(contentA, awaitItem())

            tick()
            assertEquals(null, awaitItem())

            tick()
            assertEquals(contentA, awaitItem())
        }
    }

    @Test
    fun cycleBetweenWithDurationProducesTheCorrectValues() = runTest {
        cycleBetween(5.seconds, flowA, flowB).test {
            assertEquals(contentA, awaitItem())

            val b = measureTimedValue { awaitItem() }
            assertEquals(contentB, b.value)

            val a = measureTimedValue { awaitItem() }
            assertEquals(contentA, a.value)
        }
    }

    @Test
    fun cycleBetweenWithDurationHasProperTiming() = runTest {
        cycleBetween(5.seconds, flowA, flowB).test {
            assertEquals(contentA, awaitItem())

            val b = measureTimedValue { awaitItem() }
            assertEquals(5.seconds, b.duration)

            val a = measureTimedValue { awaitItem() }
            assertEquals(5.seconds, a.duration)
        }
    }

    @Test
    fun cycleBetweenWithInitialDelayWorks() = runTest {
        cycleBetween(
            interval = 5.seconds,
            initialDelay = 2.seconds,
            flow1 = flowA,
            flow2 = flowB
        ).test {
            val initial = measureTimedValue { awaitItem() }
            assertEquals(contentA, initial.value)
            assertEquals(2.seconds, initial.duration)

            val subsequent = measureTimedValue { awaitItem() }
            assertEquals(5.seconds, subsequent.duration)
            assertEquals(contentB, subsequent.value)
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

    @OptIn(ExperimentalTime::class)
    private suspend fun <T> TestScope.measureTimedValue(block: suspend () -> T): TimedValue<T> {
        return testScheduler.timeSource.measureTimedValue { block() }
    }

    private fun assertEquals(expected: Duration, actual: Duration) {
        assertEquals(expected.inWholeMicroseconds, actual.inWholeMicroseconds)
    }
}
