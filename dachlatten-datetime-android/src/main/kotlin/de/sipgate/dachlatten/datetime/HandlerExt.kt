package de.sipgate.dachlatten.datetime

import android.os.Handler
import kotlin.time.Duration
import androidx.core.os.postDelayed

/**
 * Version of [Handler.postDelayed] which re-orders the parameters, allowing the action to be placed
 * outside of parentheses and takes {Duration}s.
 *
 * ```
 * handler.postDelayed(200.milliseconds) {
 *     doSomething()
 * }
 * ```
 *
 * @return the created Runnable
 */
@Suppress("unused")
inline fun Handler.postDelayed(
    delay: Duration,
    token: Any? = null,
    crossinline action: () -> Unit
) = postDelayed(
    delayInMillis = delay.inWholeMilliseconds,
    token = token,
    action = action
)
