package de.sipgate.dachlatten.primitives

/**
 * This typealias exists only for aesthetic reasons.
 * Declaring a HandlerFunc return type reads easier than having to
 * parse function declaration syntax.
 */
typealias HandlerFunc = () -> Unit

val EmptyHandlerFunc: HandlerFunc = {}
