package de.sipgate.dachlatten.primitives

public infix fun HandlerFunc.then(other: HandlerFunc): () -> Unit = {
    invoke()
    other()
}
