package de.sipgate.dachlatten.compose.lifecycle

import androidx.compose.ui.test.junit4.createComposeRule
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import kotlin.test.assertTrue

@RunWith(RobolectricTestRunner::class)
class LifecycleObserverTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun testLifecycleObserver() {
        var called = false

        composeTestRule.setContent {
            LifecycleObserver(lifecycleObserver = object : DefaultLifecycleObserver {
                override fun onStart(owner: LifecycleOwner) {
                    called = true
                }
            })
        }

        assertTrue(called)
    }
}
