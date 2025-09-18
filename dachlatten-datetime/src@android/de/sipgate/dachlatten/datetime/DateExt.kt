package de.sipgate.dachlatten.datetime

import android.os.Build
import android.support.annotation.RequiresApi
import java.time.LocalDateTime
import java.util.Date
import java.util.TimeZone

/**
 * Convenience converter from Java Date to the new DateTime API
 */
@RequiresApi(Build.VERSION_CODES.O)
public fun Date.toLocalDateTime(zone: TimeZone): LocalDateTime = LocalDateTime.ofInstant(toInstant(), zone.toZoneId())
