package ru.sug4chy

import ru.sug4chy.repository.ExpenseRepository
import ru.sug4chy.repository.implementation.FileSystemExpenseRepository
import ru.sug4chy.usecase.ExpenseUseCase
import ru.sug4chy.usecase.implementation.ExpenseUseCaseImpl

val expenseRepository: ExpenseRepository = FileSystemExpenseRepository()
val expenseUseCase: ExpenseUseCase = ExpenseUseCaseImpl(expenseRepository)

fun main(args: Array<String>) {
    try {
        require(args.isNotEmpty()) { "You must specify command and it's arguments." }

        val commandArguments = args.drop(1).toTypedArray()
        when (args[0]) {
            "add" -> handleAddCommand(commandArguments)
            "update" -> handleUpdateCommand(commandArguments)
        }
    } catch (e: Exception) {
        handleException(e)
    }
}

private fun handleException(e: Throwable) =
    println("\u001B[31m${e.message}\u001B[0m")

private fun handleAddCommand(args: Array<String>) {
    // General validation
    require(args.size == 4) { "Too many or not enough arguments: ${args.size}. Expected 4." }

    // Validate description parameter
    require(args.contains("--description")) { "Description argument is required." }
    require(args.indexOf("--description") != args.lastIndex) { "Description argument is required." }
    require(!args[args.indexOf("--description") + 1].startsWith("--")) { "Description argument is required." }

    // Validate amount parameter
    require(args.contains("--amount")) { "Amount argument is required." }
    require(args.indexOf("--amount") != args.lastIndex) { "Amount argument is required." }
    require(args[args.indexOf("--amount") + 1].toDoubleOrNull() != null) { "Amount must be valid number." }

    val description = args[args.indexOf("--description") + 1]
    val amount = args[args.indexOf("--amount") + 1].toDouble()

    expenseUseCase.createExpense(description, amount).also {
        println("Expense added successfully (ID: $it)")
    }
}

private fun handleUpdateCommand(args: Array<String>) {
    // General validation
    require(args.size in 4..6)
    require(args.contains("--description") || args.contains("--amount")) {
        "You need to specify field that you want to edit."
    }

    // Validate ID parameter
    require(args.contains("--id")) { "ID argument is required." }
    require(args.indexOf("--id") != args.lastIndex) { "ID argument is required." }
    require(args[args.indexOf("--id") + 1].toLongOrNull() != null) { "ID must be valid number." }

    // Validate description parameter
    if (args.contains("--description")) {
        require(args.indexOf("--description") != args.lastIndex) { "You didn't specify description." }
        require(!args[args.indexOf("--description") + 1].startsWith("--")) { "You didn't specify description." }
    }

    // Validate amount parameter
    if (args.contains("--amount")) {
        require(args.indexOf("--amount") != args.lastIndex) { "You didn't specify amount." }
        require(args[args.indexOf("--amount") + 1].toDoubleOrNull() != null) { "You didn't specify amount." }
    }

    val id = args[args.indexOf("--id") + 1].toLong()
    val description = if (args.contains("--description")) args[args.indexOf("--description") + 1] else null
    val amount = if (args.contains("--amount")) args[args.indexOf("--amount") + 1].toDouble() else null

    expenseUseCase.updateExpense(id, description, amount).fold(
        onSuccess = { _ -> println("Expense updated successfully (ID: $id)") },
        onFailure = { error -> handleException(error) }
    )
}