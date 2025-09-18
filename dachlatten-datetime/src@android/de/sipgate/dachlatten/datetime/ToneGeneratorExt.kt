package de.sipgate.dachlatten.datetime

import android.media.ToneGenerator
import kotlin.time.Duration

@Suppress("unused")
public fun ToneGenerator.startTone(toneType: Int, duration: Duration): Boolean = startTone(
    toneType, duration.inWholeMilliseconds.toInt()
)
