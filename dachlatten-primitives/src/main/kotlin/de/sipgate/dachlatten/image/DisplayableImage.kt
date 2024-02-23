package de.sipgate.dachlatten.image

import kotlinx.serialization.Serializable

typealias ImageUrl = String

@Serializable
data class DisplayableImage(val dark: ImageUrl, val light: ImageUrl)
