package de.sipgate.dachlatten.compose

import androidx.compose.ui.test.junit4.createComposeRule
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.RuntimeEnvironment
import kotlin.test.assertTrue

@RunWith(RobolectricTestRunner::class)
class AndroidHandlerFuncComposeTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun testAndroidHandlerFuncWillReceiveComposeContext() {
        composeTestRule.setContent {
            val handlerFunc: () -> Unit = withContext(::someFunctionThatAccessesTheAndroidContext)
            handlerFunc.invoke()
        }
    }

    @Test
    fun testAndroidHandlerFuncWithReturnValueWillReceiveComposeContextAndReturnValue() {
        composeTestRule.setContent {
            val handlerFunc: () -> String = withContext(::someFunctionThatAccessesTheAndroidContextAndReturnsSomething)
            handlerFunc.invoke()
        }
    }

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

    context (ContextProvider)
    private fun someFunctionThatAccessesTheAndroidContext() {
        context.packageName
    }

    context (ContextProvider)
    private fun someFunctionThatAccessesTheAndroidContextAndReturnsSomething(): String {
        return context.packageName
    }
}
