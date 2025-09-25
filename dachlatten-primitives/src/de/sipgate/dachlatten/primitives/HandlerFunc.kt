package de.sipgate.dachlatten.primitives

/**
 * This typealias exists only for aesthetic reasons.
 * Declaring a HandlerFunc return type reads easier than having to
 * parse function declaration syntax.
 */
public typealias HandlerFunc = () -> Unit

public val EmptyHandlerFunc: HandlerFunc = {}
