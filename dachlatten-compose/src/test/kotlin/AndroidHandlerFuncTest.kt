package de.sipgate.dachlatten.compose

import android.content.Context
import org.junit.jupiter.api.Test

class AndroidHandlerFuncTest {

    private lateinit var context: Context

    @Test
    fun testAndroidHandlerFuncWillReceiveAContext() {

        val handlerFunc = context.withContext(::someFunctionThatAccessesTheAndroidContext)

        handlerFunc.invoke()
    }

    context (ContextProvider)
    private fun someFunctionThatAccessesTheAndroidContext() {
        context.applicationInfo.name
    }
}
