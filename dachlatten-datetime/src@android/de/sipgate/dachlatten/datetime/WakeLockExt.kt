package de.sipgate.dachlatten.datetime

import android.os.PowerManager.WakeLock
import kotlin.time.Duration

@Suppress("unused")
public fun WakeLock.acquire(duration: Duration) {
    acquire(duration.inWholeMilliseconds)
}
