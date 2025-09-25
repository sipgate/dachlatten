package de.sipgate.dachlatten.compose

import android.content.Context


private const val DEFAULT_LANGUAGE = "en"

public val Context.language: String
    get() = resources.configuration.primaryLocale.language ?: DEFAULT_LANGUAGE
