package de.sipgate.dachlatten.primitives

import java.security.MessageDigest

val String.sha256: String
    get() = MessageDigest
        .getInstance("SHA-256")
        .digest(toByteArray())
        .joinToString(separator = "")  { "%02x".format(it) }
