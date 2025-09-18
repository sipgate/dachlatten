package de.sipgate.dachlatten.datetime

import android.os.PowerManager.WakeLock
import kotlin.time.Duration

@Suppress("unused")
fun WakeLock.acquire(duration: Duration) = acquire(duration.inWholeMilliseconds)
