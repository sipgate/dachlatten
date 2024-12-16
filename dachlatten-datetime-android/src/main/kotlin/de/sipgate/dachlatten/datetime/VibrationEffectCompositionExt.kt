package de.sipgate.dachlatten.datetime

import android.os.Build
import android.os.VibrationEffect.Composition
import androidx.annotation.FloatRange
import androidx.annotation.RequiresApi
import kotlin.time.Duration

@Suppress("unused")
@RequiresApi(Build.VERSION_CODES.R)
fun Composition.addPrimitive(
    primitiveId: Int,
    @FloatRange(from = 0.0, to = 1.0) scale: Float,
    delay: Duration
): Composition = addPrimitive(
    /* primitiveId = */ primitiveId,
    /* scale = */ scale,
    /* delay = */ delay.inWholeMilliseconds.toInt()
)
