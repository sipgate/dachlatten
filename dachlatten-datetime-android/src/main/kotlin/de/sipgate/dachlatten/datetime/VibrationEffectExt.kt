package de.sipgate.dachlatten.datetime

import android.os.Build
import android.os.VibrationEffect
import androidx.annotation.RequiresApi
import kotlin.time.Duration

@Suppress("unused")
@RequiresApi(Build.VERSION_CODES.O)
fun createWaveform(timings: Array<Duration>, amplitudes: IntArray, repeat: Int): VibrationEffect =
    VibrationEffect.createWaveform(
        /* timings = */ timings.map { it.inWholeMilliseconds }.toLongArray(),
        /* amplitudes = */ amplitudes,
        /* repeat = */ repeat
    )
