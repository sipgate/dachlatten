package de.sipgate.dachlatten.datetime

import androidx.core.app.NotificationCompat.Builder
import kotlin.time.Duration

@Suppress("unused")
fun Builder.setTimeoutAfter(duration: Duration): Builder =
    setTimeoutAfter(duration.inWholeMilliseconds)

@Suppress("unused")
fun Builder.setVibrate(pattern: Array<Duration>?): Builder =
    setVibrate(pattern?.map { it.inWholeMilliseconds }?.toLongArray())
