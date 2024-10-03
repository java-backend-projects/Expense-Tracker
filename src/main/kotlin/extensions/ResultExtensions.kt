package ru.sug4chy.extensions

fun Result.Companion.success(): Result<Unit> = this.success(Unit)