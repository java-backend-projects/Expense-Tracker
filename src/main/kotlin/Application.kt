package ru.sug4chy

import ru.sug4chy.repository.implementation.FileSystemExpenseRepository
import ru.sug4chy.usecase.implementation.ExpenseUseCaseImpl

fun main(args: Array<String>) {
    try {
        val useCase = ExpenseUseCaseImpl(FileSystemExpenseRepository())
        useCase.createExpense("desc", 1.0)
    } catch(e: Exception) {
        handleException(e)
    }
}

private fun handleException(e: Throwable) {
    println("\u001B[31m${e.message}\u001B[0m")
}