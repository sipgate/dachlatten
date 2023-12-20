package de.sipgate.dachlatten.google

import android.app.Activity
import com.google.android.gms.tasks.OnFailureListener
import com.google.android.gms.tasks.OnSuccessListener
import com.google.android.gms.tasks.Task
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertInstanceOf
import org.junit.jupiter.api.Assertions.assertNull
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import java.util.concurrent.Executor

class TaskAwaitResultTest {
    @Test
    fun resultHandlesSuccessCorrectly() = runTest {
        val successfulTask = DummyResult(success = "success")

        val result = successfulTask.awaitResult()
        assertTrue(result.isSuccess)
        assertEquals("success", result.getOrNull())
        assertFalse(result.isFailure)
        assertNull(result.exceptionOrNull())
    }

    @Test
    fun resultHandlesErrorCorrectly() = runTest {
        val failingTask = DummyResult<String>(error = Exception("you shall not pass!"))

        val result = failingTask.awaitResult()
        assertFalse(result.isSuccess)
        assertNull(result.getOrNull())
        assertTrue(result.isFailure)
        assertInstanceOf(Exception::class.java, result.exceptionOrNull())
    }

    private class DummyResult<T>(
        private val success: T? = null,
        private val error: Throwable? = null
    ) : Task<T>() {
        override fun isComplete(): Boolean = true

        override fun isSuccessful(): Boolean = success != null

        override fun isCanceled(): Boolean = false

        override fun getResult(): T? = success

        override fun getException(): java.lang.Exception? = error as? java.lang.Exception
        override fun addOnSuccessListener(p0: Activity, p1: OnSuccessListener<in T>): Task<T> {
            TODO("Not yet implemented")
        }

        override fun addOnSuccessListener(p0: Executor, p1: OnSuccessListener<in T>): Task<T> {
            TODO("Not yet implemented")
        }

        override fun addOnSuccessListener(p0: OnSuccessListener<in T>): Task<T> {
            TODO("Not yet implemented")
        }

        override fun <X : Throwable?> getResult(p0: Class<X>): T? {
            TODO("Not yet implemented")
        }

        override fun addOnFailureListener(p0: OnFailureListener): Task<T> {
            TODO("Not yet implemented")
        }

        override fun addOnFailureListener(p0: Executor, p1: OnFailureListener): Task<T> {
            TODO("Not yet implemented")
        }

        override fun addOnFailureListener(p0: Activity, p1: OnFailureListener): Task<T> {
            TODO("Not yet implemented")
        }
    }
}
