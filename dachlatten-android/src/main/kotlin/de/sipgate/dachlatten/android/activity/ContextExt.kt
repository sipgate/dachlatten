package de.sipgate.dachlatten.android.activity

import android.app.Activity
import android.content.Context
import android.content.ContextWrapper
import androidx.activity.ComponentActivity

fun Context.getActivity(): Activity? =
    when (this) {
        is ComponentActivity -> this
        is ContextWrapper -> baseContext.getActivity()
        else -> null
    }

val Context.languageTag: String
    get() = resources.configuration.locales[0].toLanguageTag() ?: "en-GB"
