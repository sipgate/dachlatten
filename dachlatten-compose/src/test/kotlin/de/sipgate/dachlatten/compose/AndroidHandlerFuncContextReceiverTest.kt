package de.sipgate.dachlatten.compose

import android.content.Context
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.RuntimeEnvironment
import kotlin.test.assertTrue

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
        assertTrue(result.isNotEmpty())
    }

    @Test
    fun testAndroidHandlerFuncWillReceiveAContextAndPassParameterThrough() {
        val context = RuntimeEnvironment.getApplication().applicationContext
        val handlerFunc = context.withContext(::someFunctionThatReceivesAParameter)

        val result = handlerFunc.invoke(20)
        assertTrue(result.isNotEmpty())
    }

    context (context: Context)
    private fun someFunctionThatAccessesTheAndroidContext() {
        context.packageName
    }

    context (context: Context)
    private fun someFunctionThatAccessesTheAndroidContextAndReturnsSomething(): String {
        return context.packageName
    }

    context (context: Context)
    private fun someFunctionThatReceivesAParameter(parameter: Int): String {
        return context.packageName + parameter.toString()
    }
}
