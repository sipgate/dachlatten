package de.sipgate.dachlatten.flow

import app.cash.turbine.test
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.test.assertEquals

class WindowedTest {

    @Test
    fun testWindowedWithSize2AndStep1() = runTest {
        flowOf(1, 2, 3, 4).windowed(2, 1).test {
            assertEquals(listOf(1, 2), awaitItem())
            assertEquals(listOf(2, 3), awaitItem())
            assertEquals(listOf(3, 4), awaitItem())
            awaitComplete()
        }
    }

    @Test
    fun testWindowedWithSize2AndStep2() = runTest {
        flowOf(1, 2, 3, 4).windowed(2, 2).test {
            assertEquals(listOf(1, 2), awaitItem())
            assertEquals(listOf(3, 4), awaitItem())
            awaitComplete()
        }
    }

    @Test
    fun testWindowedWithSize3AndStep1() = runTest {
        flowOf(1, 2, 3, 4).windowed(3, 1).test {
            assertEquals(listOf(1, 2, 3), awaitItem())
            assertEquals(listOf(2, 3, 4), awaitItem())
            awaitComplete()
        }
    }

    @Test
    fun testWindowedWithSize3AndStep2() = runTest {
        flowOf(1, 2, 3, 4).windowed(3, 2).test {
            assertEquals(listOf(1, 2, 3), awaitItem())
            awaitComplete()
        }
    }
}
