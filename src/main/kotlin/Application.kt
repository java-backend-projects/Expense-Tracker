package ru.sug4chy

import com.github.ajalt.clikt.core.UsageError
import com.github.ajalt.clikt.core.main
import com.github.ajalt.clikt.core.subcommands
import ru.sug4chy.cli.ExpenseTracker
import ru.sug4chy.cli.commands.AddCommand
import ru.sug4chy.cli.commands.DeleteCommand
import ru.sug4chy.cli.commands.UpdateCommand
import ru.sug4chy.repository.ExpenseRepository
import ru.sug4chy.repository.implementation.FileSystemExpenseRepository
import ru.sug4chy.usecase.ExpenseUseCase
import ru.sug4chy.usecase.implementation.ExpenseUseCaseImpl
import kotlin.system.exitProcess

val expenseRepository: ExpenseRepository = FileSystemExpenseRepository()
val expenseUseCase: ExpenseUseCase = ExpenseUseCaseImpl(expenseRepository)

fun main(args: Array<String>) =
    try {
        ExpenseTracker()
            .subcommands(
                AddCommand(expenseUseCase),
                UpdateCommand(expenseUseCase),
                DeleteCommand(expenseUseCase),
            )
            .main(args)
    } catch (e: Exception) {
        ExpenseTracker().echoFormattedHelp(UsageError(e.message))
        exitProcess(1)
    }