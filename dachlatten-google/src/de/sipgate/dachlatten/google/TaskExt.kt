package de.sipgate.dachlatten.google

import com.google.android.gms.tasks.Task
import kotlinx.coroutines.tasks.await

suspend fun <T> Task<T>.awaitResult(): Result<T> {
    return try {
        Result.success(await())
    } catch (e: Throwable) {
        Result.failure(e)
    }
}
