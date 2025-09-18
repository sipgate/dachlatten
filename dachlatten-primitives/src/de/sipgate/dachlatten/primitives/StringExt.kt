package de.sipgate.dachlatten.primitives

import java.security.MessageDigest

val String.sha256: String
    get() = MessageDigest
        .getInstance("SHA-256")
        .digest(toByteArray())
        .joinToString(separator = "")  { "%02x".format(it) }

fun String.trimWhitespace(): String = this.trimIndent().replace("\n", "")

fun String.ensureEndsWithSlash(): String = if (last() == '/') this else "$this/"

fun String.nullIfEmpty() = ifEmpty { null }

infix fun String?.or(other: String): String =
    when {
        this == null -> other
        isEmpty() -> other
        isBlank() -> other
        else -> this
    }
