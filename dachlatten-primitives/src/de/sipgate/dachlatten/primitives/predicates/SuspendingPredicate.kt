package de.sipgate.dachlatten.primitives.predicates

import kotlinx.coroutines.runBlocking

public interface SuspendingPredicate<T> : Predicate<T> {

    override fun invoke(test: T): Boolean = runBlocking { invokeSuspending(test) }

    public suspend fun invokeSuspending(test: T): Boolean
}
