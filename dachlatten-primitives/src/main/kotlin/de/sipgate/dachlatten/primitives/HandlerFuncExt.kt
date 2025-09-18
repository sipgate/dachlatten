package de.sipgate.dachlatten.primitives

infix fun HandlerFunc.then(other: HandlerFunc): () -> Unit = {
    invoke()
    other()
}
