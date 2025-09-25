package de.sipgate.dachlatten.compose

import de.sipgate.dachlatten.primitives.EmptyHandlerFunc
import de.sipgate.dachlatten.primitives.HandlerFunc
import kotlin.test.Test

class HandlerFuncTest {
    @Test
    fun testClickHandlerAndHandlerFuncCanBeUsedInterchangeably() {
        val clickHandler: ClickHandler = EmptyHandlerFunc
        val handlerFunc: HandlerFunc = clickHandler
        handlerFunc.invoke()
    }
}
