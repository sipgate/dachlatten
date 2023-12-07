package de.sipgate.dachlatten.flow

import app.cash.turbine.test
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.advanceTimeBy
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import kotlin.time.Duration.Companion.seconds

class FlowCyclerTest {

    @Test
    fun cycleBetweenSwitchesTheFlowBeingEmitted() = runTest {
        val flow1 = flowOf("flow1")
        val flow2 = flowOf("flow2")

        val trigger = MutableStateFlow(1)

        cycleBetween(trigger, flow1, flow2).test {
            assertEquals("flow1", awaitItem())
            trigger.value++
            assertEquals("flow2", awaitItem())
            trigger.value++
            assertEquals("flow1", awaitItem())
        }
    }

    @Test
    fun cycleBetweenWorksWithNullValues() = runTest {
        val flow1 = flowOf("flow1")
        val flow2 = flowOf<String?>(null)

        val trigger = MutableStateFlow(1)

        cycleBetween(trigger, flow1, flow2).test {
            assertEquals("flow1", awaitItem())
            trigger.value++
            assertEquals(null, awaitItem())
            trigger.value++
            assertEquals("flow1", awaitItem())
        }
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun cycleBetweenWithDurationWorks() = runTest {
        val flow1 = flowOf("flow1")
        val flow2 = flowOf<String?>("flow2")

        val trigger = MutableStateFlow(1)

        cycleBetween(5.seconds, flow1, flow2).test {
            assertEquals("flow1", awaitItem())
            expectNoEvents()
            advanceTimeBy(5.seconds)
            assertEquals("flow2", awaitItem())
            expectNoEvents()
            advanceTimeBy(5.seconds)
            assertEquals("flow1", awaitItem())
        }
    }
}
