package de.sipgate.dachlatten.datetime

import android.animation.ValueAnimator
import kotlin.time.Duration

@Suppress("unused")
fun ValueAnimator.setDuration(duration: Duration): ValueAnimator =
    setDuration(duration.inWholeMilliseconds)

@Suppress("unused")
fun ValueAnimator.setStartDelay(duration: Duration) {
    setStartDelay(duration.inWholeMilliseconds)
}
