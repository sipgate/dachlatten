package de.sipgate.dachlatten.io

import kotlinx.io.Source
import kotlinx.io.buffered
import kotlinx.io.files.Path
import kotlinx.io.files.SystemFileSystem

public inline fun readResource(
    fileName: String,
    resourcesDir: String = "testResources",
    process: (Source) -> Unit
) {
    val path = Path(resourcesDir, fileName)
    SystemFileSystem.source(path).use { fileSource ->
        process(fileSource.buffered())
    }
}
