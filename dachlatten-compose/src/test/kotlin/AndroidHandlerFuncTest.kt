package de.sipgate.dachlatten.compose

import org.junit.Test
import org.junit.jupiter.api.Assertions
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.RuntimeEnvironment

@RunWith(RobolectricTestRunner::class)
class AndroidHandlerFuncTest {

    @Test
    fun testAndroidHandlerFuncWillReceiveAContext() {
        val context = RuntimeEnvironment.getApplication().applicationContext
        val handlerFunc = context.withContext(::someFunctionThatAccessesTheAndroidContext)

        handlerFunc.invoke()
    }

    @Test
    fun testAndroidHandlerFuncWillReceiveAContextAndReturnValueIsPassedBack() {
        val context = RuntimeEnvironment.getApplication().applicationContext
        val handlerFunc = context.withContext(::someFunctionThatAccessesTheAndroidContextAndReturnsSomething)

        val result = handlerFunc.invoke()
        Assertions.assertTrue(result.isNotEmpty())
    }

    context (ContextProvider)
    private fun someFunctionThatAccessesTheAndroidContext() {
        context.packageName
    }

    context (ContextProvider)
    private fun someFunctionThatAccessesTheAndroidContextAndReturnsSomething(): String {
        return context.packageName
    }
}
