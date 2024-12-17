package de.sipgate.dachlatten.datetime

import android.view.ViewPropertyAnimator
import kotlin.time.Duration

@Suppress("unused")
fun ViewPropertyAnimator.setDuration(duration: Duration): ViewPropertyAnimator =
    setDuration(duration.inWholeMilliseconds)

@Suppress("unused")
fun ViewPropertyAnimator.setStartDelay(duration: Duration): ViewPropertyAnimator =
    setStartDelay(duration.inWholeMilliseconds)
