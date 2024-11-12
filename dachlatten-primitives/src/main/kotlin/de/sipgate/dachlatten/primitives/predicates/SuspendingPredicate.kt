package de.sipgate.dachlatten.primitives.predicates

import kotlinx.coroutines.runBlocking

interface SuspendingPredicate<T> : Predicate<T> {

    override fun invoke(test: T): Boolean = runBlocking { invokeSuspending(test) }

    suspend fun invokeSuspending(test: T): Boolean
}
