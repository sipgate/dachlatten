package de.sipgate.dachlatten.android.context

import android.content.Context
import android.content.res.Configuration
import android.os.Build
import de.sipgate.dachlatten.primitives.nullIfEmpty
import java.util.Locale

internal const val DEFAULT_LANGUAGE = "en"

public val Context.language: String
    get() = resources.configuration.primaryLocale.language.nullIfEmpty() ?: DEFAULT_LANGUAGE

public val Configuration.primaryLocale: Locale
    get() = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
        locales[0]
    } else {
        @Suppress("DEPRECATION")
        locale
    }
