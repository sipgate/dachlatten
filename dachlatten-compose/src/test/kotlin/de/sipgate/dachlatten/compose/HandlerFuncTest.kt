package de.sipgate.dachlatten.compose

import kotlin.test.Test
import kotlin.test.assertTrue

class HandlerFuncTest {
    @Test
    fun testOrdinaryLambdaCanBeCastToHandlerFunc() {
        var wasExecuted = false
        val handlerFunc: HandlerFunc = { wasExecuted = true }

        handlerFunc.invoke()
        assertTrue(wasExecuted)
    }

    @Test
    fun testEmptyHandlerCanBeAssignedToOrdinaryFunctionReference() {
        val function: () -> Unit = EmptyHandlerFunc
        function.invoke()
    }

    @Test
    fun testClickHandlerAndHandlerFuncCanBeUsedInterchangeably() {
        val clickHandler: ClickHandler = EmptyHandlerFunc
        val handlerFunc: HandlerFunc = clickHandler
        handlerFunc.invoke()
    }
}
