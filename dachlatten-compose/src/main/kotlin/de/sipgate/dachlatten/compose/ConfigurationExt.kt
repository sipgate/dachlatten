package de.sipgate.dachlatten.compose

import android.content.res.Configuration
import android.os.Build
import androidx.compose.ui.text.intl.Locale

val Configuration.primaryLocale: Locale
    get() = Locale(
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            locales[0]
        } else {
            @Suppress("DEPRECATION")
            locale
        }.language
    )

fun Configuration.resolveLocale(supportedLanguages: Set<String>) =
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
        locales.getFirstMatch(supportedLanguages.toTypedArray())
    } else {
        @Suppress("DEPRECATION")
        locale
    }?.let { Locale(it.language) }

