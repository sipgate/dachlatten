package de.sipgate.dachlatten.debug

import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test

class StacktraceKtTest {

    @Test
    fun stacktraceReturnsStacktrace() {
        val result = stacktrace

        assertTrue(result.contains("stacktraceReturnsStacktrace"))
    }

    @Test
    fun stacktraceReturnsInnerFuncStacktrace() {
        fun innerFunc() = stacktrace

        val result = innerFunc()

        assertTrue(result.contains("\$innerFunc"))
    }
}
