package de.sipgate.dachlatten.primitives

import kotlin.test.Test
import kotlin.test.assertEquals

class HandlerFuncExtTest {
    @Test
    fun thenExecutesBothBranchesInOrderExactlyOnce() {
        var buffer = ""
        val funcA: HandlerFunc = { buffer += "a" }
        val funcB: HandlerFunc = { buffer += "b" }
        val func = funcA then funcB

        func()

        assertEquals("ab", buffer)
    }
}
