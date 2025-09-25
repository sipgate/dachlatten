package de.sipgate.dachlatten.primitives

import java.security.MessageDigest

public val String.sha256: String
    get() = MessageDigest
        .getInstance("SHA-256")
        .digest(toByteArray())
        .joinToString(separator = "")  { "%02x".format(it) }

public fun String.trimWhitespace(): String = this.trimIndent().replace("\n", "")

public fun String.ensureEndsWithSlash(): String = if (last() == '/') this else "$this/"

public fun String.nullIfEmpty(): String? = ifEmpty { null }

public infix fun String?.or(other: String): String =
    when {
        this == null -> other
        isEmpty() -> other
        isBlank() -> other
        else -> this
    }
