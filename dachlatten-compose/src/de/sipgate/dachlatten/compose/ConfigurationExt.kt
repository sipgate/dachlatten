package de.sipgate.dachlatten.compose

import android.content.res.Configuration
import android.os.Build
import java.util.Locale

val Configuration.primaryLocale: Locale
    get() = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
        locales[0]
    } else {
        @Suppress("DEPRECATION")
        locale
    }

fun Configuration.resolveLocale(supportedLanguages: Set<String>): Locale? {
    return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
        locales.getFirstMatch(supportedLanguages.toTypedArray())
    } else {
        @Suppress("DEPRECATION")
        locale
    }
}
