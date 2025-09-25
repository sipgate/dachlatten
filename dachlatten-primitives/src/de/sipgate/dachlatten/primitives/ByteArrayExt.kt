package de.sipgate.dachlatten.primitives

private const val HEX_BITS_PER_CHAR = 16
private const val BYTE_PADDING = 2

@ExperimentalUnsignedTypes
public fun ByteArray.toHexString(): String = asUByteArray()
    .joinToString("") {
        it.toString(HEX_BITS_PER_CHAR)
            .padStart(BYTE_PADDING, '0')
    }

public fun String.hexStringToByteArray(): ByteArray = chunked(2)
    .map { it.trim().toInt(HEX_BITS_PER_CHAR).toByte() }
    .toByteArray()
