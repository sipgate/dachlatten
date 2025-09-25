package de.sipgate.dachlatten.android

import android.content.Context
import java.util.Locale

internal fun setLocale(language: String, country: String? = null) {
    val locale = country?.let { Locale(language, country) }
        ?: Locale(language)

    Locale.setDefault(locale)

    val res = context.resources
    val config = res.configuration
    config.locale = locale
    res.updateConfiguration(config, res.displayMetrics)
}
