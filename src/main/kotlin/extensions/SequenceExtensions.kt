package ru.sug4chy.extensions

fun Sequence<String>.withoutHeaders(): Sequence<String> =
    this.drop(1)