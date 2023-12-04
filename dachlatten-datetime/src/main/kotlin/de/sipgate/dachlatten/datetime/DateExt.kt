package de.sipgate.dachlatten.datetime

import android.os.Build
import java.time.LocalDateTime
import java.util.Date
import java.util.TimeZone

@android.support.annotation.RequiresApi(Build.VERSION_CODES.O)
fun Date.toLocalDateTime(zone: TimeZone): LocalDateTime = LocalDateTime.ofInstant(toInstant(), zone.toZoneId())
