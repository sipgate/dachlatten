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

    context (Context)
    private fun someFunctionThatAccessesTheAndroidContext() {
        packageName
    }

    context (Context)
    private fun someFunctionThatAccessesTheAndroidContextAndReturnsSomething(): String {
        return packageName
    }
}
