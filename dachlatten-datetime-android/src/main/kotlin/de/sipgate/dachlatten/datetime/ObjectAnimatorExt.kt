package de.sipgate.dachlatten.datetime

import android.animation.ObjectAnimator
import kotlin.time.Duration

@Suppress("unused")
fun ObjectAnimator.setDuration(duration: Duration): ObjectAnimator =
    setDuration(duration.inWholeMilliseconds)
