package de.sipgate.dachlatten.datetime

import android.Manifest
import android.os.Vibrator
import androidx.annotation.RequiresPermission
import kotlin.time.Duration

@Suppress("unused")
@RequiresPermission(Manifest.permission.VIBRATE)
fun Vibrator.vibrate(duration: Duration) {
    @Suppress("DEPRECATION")
    vibrate(duration.inWholeMilliseconds)
}
