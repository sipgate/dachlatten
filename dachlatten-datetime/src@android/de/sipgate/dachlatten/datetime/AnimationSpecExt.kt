package de.sipgate.dachlatten.datetime

import androidx.compose.animation.core.StartOffsetType
import kotlin.time.Duration

@Suppress("unused")
fun StartOffset(offset: Duration, offsetType: StartOffsetType = StartOffsetType.Delay) =
    androidx.compose.animation.core.StartOffset(
        offsetMillis = offset.inWholeMilliseconds.toInt(),
        offsetType = offsetType
    )
