package de.sipgate.dachlatten.google

import com.google.android.gms.tasks.Task
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertInstanceOf
import org.junit.jupiter.api.Assertions.assertNull
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Disabled
import org.junit.jupiter.api.Test

class TaskAwaitResultTest {
    @Test
    @Disabled
    fun resultHandlesSuccessCorrectly() = runTest {
        val successfulTask = mockk<Task<String>> {
            coEvery { await() } returns "success"
        }

        val result = successfulTask.awaitResult()
        assertTrue(result.isSuccess)
        assertEquals("success", result.getOrNull())
        assertFalse(result.isFailure)
        assertNull(result.exceptionOrNull())
    }

    @Test
    @Disabled
    fun resultHandlesErrorCorrectly() = runTest {
        val failingTask = mockk<Task<String>> {
            coEvery { await() } throws Exception("you shall not pass!")
        }

        val result = failingTask.awaitResult()
        assertFalse(result.isSuccess)
        assertNull(result.getOrNull())
        assertTrue(result.isFailure)
        assertInstanceOf(Exception::class.java, result.exceptionOrNull())
    }
}
