package de.sipgate.dachlatten.flow

import kotlinx.coroutines.flow.SharingStarted
import kotlin.time.Duration

fun WhileSubscribed(duration: Duration): SharingStarted {
    return SharingStarted.WhileSubscribed(stopTimeoutMillis = duration.inWholeMilliseconds)
}
