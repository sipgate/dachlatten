package de.sipgate.dachlatten.datetime

import android.animation.ValueAnimator
import kotlin.time.Duration

@Suppress("unused")
public fun ValueAnimator.setDuration(duration: Duration): ValueAnimator =
    setDuration(duration.inWholeMilliseconds)

@Suppress("unused")
public fun ValueAnimator.setStartDelay(duration: Duration) {
    setStartDelay(duration.inWholeMilliseconds)
}
