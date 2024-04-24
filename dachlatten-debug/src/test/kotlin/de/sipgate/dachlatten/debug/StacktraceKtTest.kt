package de.sipgate.dachlatten.debug

import kotlin.test.Test
import kotlin.test.assertTrue

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
