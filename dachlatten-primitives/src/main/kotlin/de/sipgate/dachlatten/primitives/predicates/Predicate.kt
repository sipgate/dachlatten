package de.sipgate.dachlatten.primitives.predicates

public typealias Predicate<T> = (T) -> Boolean

public infix fun <T> Predicate<T>.and(other: Predicate<T>): Predicate<T>  = object : Predicate<T> {
    override fun invoke(test: T): Boolean {
        return this@and(test) && other(test)
    }
}

public infix fun <T> Predicate<T>.or(other: Predicate<T>): Predicate<T> = object : Predicate<T> {
    override fun invoke(test: T): Boolean {
        return this@or(test) || other(test)
    }
}

public operator fun <T> Predicate<T>.not(): Predicate<T> = { val result = this(it); !result }
