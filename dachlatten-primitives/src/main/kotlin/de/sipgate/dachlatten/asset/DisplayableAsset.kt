package de.sipgate.dachlatten.asset

import kotlinx.serialization.Serializable

typealias AssetUrl = String

@Serializable
data class DisplayableAsset(val dark: AssetUrl, val light: AssetUrl)
