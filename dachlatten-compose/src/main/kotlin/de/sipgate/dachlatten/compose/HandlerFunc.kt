package de.sipgate.dachlatten.compose

/**
 * This typealias exists only for aesthetic reasons.
 * Declaring a HandlerFunc return type reads easier than having to
 * parse function declaration syntax.
 */
typealias HandlerFunc = () -> Unit

typealias ClickHandler = HandlerFunc

val EmptyHandlerFunc: HandlerFunc = {}
