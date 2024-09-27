package de.sipgate.dachlatten.compose

import android.content.Context

interface ContextProvider {
    val context: Context
}

class ContextProviderImpl(
    override val context: Context,
) : ContextProvider
