package de.sipgate.dachlatten.compose

import android.content.Context


const val DEFAULT_LANGUAGE = "en"

val Context.language: String
    get() = resources.configuration.primaryLocale.language ?: DEFAULT_LANGUAGE
