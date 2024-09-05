package de.sipgate.dachlatten.flow

import kotlinx.coroutines.flow.SharingStarted
import kotlin.time.Duration
import kotlin.time.Duration.Companion.seconds

/**
 * This is a convenience wrapper for SharingStarted.WhileSubscribed that takes
 * Kotlin-time Duration as a parameter and converts it to milliseconds.
 *
 * @param stopTimeout configures a delay between the disappearance of the last subscriber and the
 *                    stopping of the sharing coroutine. It defaults to zero (stop immediately).
 */
fun WhileSubscribed(stopTimeout: Duration = 0.seconds): SharingStarted {
    return SharingStarted.WhileSubscribed(stopTimeoutMillis = stopTimeout.inWholeMilliseconds)
}
