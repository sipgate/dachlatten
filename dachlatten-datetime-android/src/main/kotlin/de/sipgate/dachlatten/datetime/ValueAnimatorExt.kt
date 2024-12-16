package de.sipgate.dachlatten.datetime

import android.animation.ValueAnimator
import kotlin.time.Duration

@Suppress("unused")
fun ValueAnimator.setDuration(duration: Duration): ValueAnimator =
    setDuration(duration.inWholeMilliseconds)
