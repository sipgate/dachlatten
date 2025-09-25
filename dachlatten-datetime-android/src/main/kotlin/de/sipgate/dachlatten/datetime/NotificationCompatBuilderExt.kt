package de.sipgate.dachlatten.datetime

import androidx.core.app.NotificationCompat.Builder
import kotlin.time.Duration

@Suppress("unused")
public fun Builder.setTimeoutAfter(duration: Duration): Builder =
    setTimeoutAfter(duration.inWholeMilliseconds)

@Suppress("unused")
public fun Builder.setVibrate(pattern: Array<Duration>?): Builder =
    setVibrate(pattern?.map { it.inWholeMilliseconds }?.toLongArray())
