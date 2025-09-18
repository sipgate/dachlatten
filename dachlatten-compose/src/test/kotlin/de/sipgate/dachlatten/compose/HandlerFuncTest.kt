package de.sipgate.dachlatten.compose

import de.sipgate.dachlatten.primitives.EmptyHandlerFunc
import kotlin.test.Test
import kotlin.test.assertTrue

class HandlerFuncTest {
    @Suppress("DEPRECATION")
    @Test
    fun testClickHandlerAndHandlerFuncCanBeUsedInterchangeably() {
        val clickHandler: ClickHandler = EmptyHandlerFunc
        val handlerFunc: HandlerFunc = clickHandler
        handlerFunc.invoke()
    }
}
