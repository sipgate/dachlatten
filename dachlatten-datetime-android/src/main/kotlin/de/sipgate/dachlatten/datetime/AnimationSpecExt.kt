package de.sipgate.dachlatten.datetime

import androidx.compose.animation.core.StartOffset
import androidx.compose.animation.core.StartOffsetType
import kotlin.time.Duration

@Suppress("unused")
public fun StartOffset(
    offset: Duration,
    offsetType: StartOffsetType = StartOffsetType.Delay
): StartOffset = StartOffset(
    offsetMillis = offset.inWholeMilliseconds.toInt(),
    offsetType = offsetType
)
