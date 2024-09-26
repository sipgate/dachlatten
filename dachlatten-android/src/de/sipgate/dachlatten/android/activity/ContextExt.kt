package de.sipgate.dachlatten.android.activity

import android.app.Activity
import android.content.Context
import android.content.ContextWrapper
import android.content.res.Configuration
import android.os.Build
import androidx.activity.ComponentActivity
import java.util.Locale

fun Context.getActivity(): Activity? =
    when (this) {
        is ComponentActivity -> this
        is ContextWrapper -> baseContext.getActivity()
        else -> null
    }

const val DEFAULT_LANGUAGE = "en"

val Context.language: String
    get() = resources.configuration.primaryLocale.language ?: DEFAULT_LANGUAGE

val Configuration.primaryLocale: Locale
    get() = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
        locales[0]
    } else {
        @Suppress("DEPRECATION")
        locale
    }
