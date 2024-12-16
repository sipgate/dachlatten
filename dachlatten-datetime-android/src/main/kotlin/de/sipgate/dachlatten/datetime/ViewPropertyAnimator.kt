package de.sipgate.dachlatten.datetime

import android.view.ViewPropertyAnimator
import kotlin.time.Duration

@Suppress("unused")
fun ViewPropertyAnimator.setDuration(duration: Duration): ViewPropertyAnimator =
    setDuration(duration.inWholeMilliseconds)
