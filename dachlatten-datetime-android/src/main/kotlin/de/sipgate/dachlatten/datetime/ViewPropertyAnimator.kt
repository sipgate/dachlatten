package de.sipgate.dachlatten.datetime

import android.view.ViewPropertyAnimator
import kotlin.time.Duration

@Suppress("unused")
public fun ViewPropertyAnimator.setDuration(duration: Duration): ViewPropertyAnimator =
    setDuration(duration.inWholeMilliseconds)

@Suppress("unused")
public fun ViewPropertyAnimator.setStartDelay(duration: Duration): ViewPropertyAnimator =
    setStartDelay(duration.inWholeMilliseconds)
