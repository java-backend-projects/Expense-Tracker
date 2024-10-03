package ru.sug4chy.cli.commands

import com.github.ajalt.clikt.core.CliktCommand
import com.github.ajalt.clikt.parameters.arguments.argument
import com.github.ajalt.clikt.parameters.arguments.check
import com.github.ajalt.clikt.parameters.types.long
import ru.sug4chy.usecase.ExpenseUseCase

class DeleteCommand(
    private val expenseUseCase: ExpenseUseCase
) : CliktCommand() {

    private val id: Long by argument(help = "ID of expense, that you want to remove")
        .long()
        .check("ID must be positive.") { it > 0 }

    override fun run() {
        val result = expenseUseCase.deleteExpenseById(id)

        result.fold(
            onSuccess = { echo("Expense deleted successfully (ID: $id)") },
            onFailure = { throw it }
        )
    }
}