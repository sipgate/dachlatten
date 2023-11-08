package de.sipgate.dachlatten.compose

import org.junit.Test
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

    context (ContextProvider)
    private fun someFunctionThatAccessesTheAndroidContext() {
        context.applicationInfo.name
    }
}
