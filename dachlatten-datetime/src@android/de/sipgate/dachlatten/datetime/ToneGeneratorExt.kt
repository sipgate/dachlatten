package de.sipgate.dachlatten.datetime

import android.media.ToneGenerator
import kotlin.time.Duration

@Suppress("unused")
fun ToneGenerator.startTone(toneType: Int, duration: Duration) = startTone(
    toneType, duration.inWholeMilliseconds.toInt()
)
