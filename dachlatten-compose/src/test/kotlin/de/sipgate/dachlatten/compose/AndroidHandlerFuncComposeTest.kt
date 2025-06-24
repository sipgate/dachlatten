package de.sipgate.dachlatten.compose

import android.content.Context
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
            val handlerFunc: () -> Unit = withContext<Unit> { someFunctionThatAccessesTheAndroidContext() }
            handlerFunc.invoke()
        }
    }

    @Test
    fun testAndroidHandlerFuncWithReturnValueWillReceiveComposeContextAndReturnValue() {
        composeTestRule.setContent {
            val handlerFunc: () -> String = withContext<String> { someFunctionThatAccessesTheAndroidContextAndReturnsSomething() }
            handlerFunc.invoke()
        }
    }

    @Test
    fun testAndroidHandlerFuncWillReceiveComposeContextAndPassParameterThrough() {
        composeTestRule.setContent {
            val handlerFunc = withContext<Int, String> { someFunctionThatReceivesAParameter(it) }
            val result = handlerFunc.invoke(123)
            assertTrue(result.isNotEmpty())
        }
    }

    @Test
    fun testAndroidHandlerFuncWillReceiveAContext() {
        val context = RuntimeEnvironment.getApplication().applicationContext
        val handlerFunc = context.withContext<Unit> { someFunctionThatAccessesTheAndroidContext() }
        handlerFunc.invoke()
    }

    @Test
    fun testAndroidHandlerFuncWillReceiveAContextAndReturnValueIsPassedBack() {
        val context = RuntimeEnvironment.getApplication().applicationContext
        val handlerFunc = context.withContext<String> { someFunctionThatAccessesTheAndroidContextAndReturnsSomething() }

        val result = handlerFunc.invoke()
        assertTrue(result.isNotEmpty())
    }

    @Test
    fun testAndroidHandlerFuncWillReceiveAContextAndPassParameterThrough() {
        val context = RuntimeEnvironment.getApplication().applicationContext
        val handlerFunc = context.withContext<Int, String> { someFunctionThatReceivesAParameter(it) }

        val result = handlerFunc.invoke(55)
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
