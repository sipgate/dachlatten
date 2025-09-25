package de.sipgate.dachlatten.asset

import kotlinx.serialization.Serializable

public typealias AssetUrl = String

@Serializable
public data class DisplayableAsset(
    val light: AssetUrl,
    val dark: AssetUrl? = null
)
