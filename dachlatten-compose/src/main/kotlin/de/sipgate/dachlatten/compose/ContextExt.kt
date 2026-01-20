package de.sipgate.dachlatten.compose

import android.content.Context

public val Context.language: String
    get() = resources.configuration.primaryLocale.language
