package de.sipgate.dachlatten.primitives.predicates

suspend fun <T> Iterable<T>.filter(predicate: Predicate<T>) = filter { item -> predicate(item) }

interface Predicate<T> {
    suspend operator fun invoke(test: T): Boolean
}

infix fun <T> Predicate<T>.and(other: Predicate<T>) = object : Predicate<T> {
    override suspend fun invoke(test: T): Boolean {
        return this@and(test) && other(test)
    }
}

infix fun <T> Predicate<T>.or(other: Predicate<T>) = object : Predicate<T> {
    override suspend fun invoke(test: T): Boolean {
        return this@or(test) || other(test)
    }
}

fun <T> not(other: Predicate<T>) = object : Predicate<T> {
    override suspend fun invoke(test: T): Boolean = !other(test)
}
