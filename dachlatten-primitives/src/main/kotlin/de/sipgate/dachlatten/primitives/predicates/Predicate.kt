package de.sipgate.dachlatten.primitives.predicates

typealias Predicate<T> = (T) -> Boolean

infix fun <T> Predicate<T>.and(other: Predicate<T>) = object : Predicate<T> {
    override fun invoke(test: T): Boolean {
        return this@and(test) && other(test)
    }
}

infix fun <T> Predicate<T>.or(other: Predicate<T>) = object : Predicate<T> {
    override fun invoke(test: T): Boolean {
        return this@or(test) || other(test)
    }
}

operator fun <T> Predicate<T>.not(): Predicate<T> = { val result = this(it); !result }
