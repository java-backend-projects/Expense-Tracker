package ru.sug4chy.cli.commands

import com.github.ajalt.clikt.core.CliktCommand
import com.github.ajalt.clikt.core.UsageError
import com.github.ajalt.clikt.parameters.arguments.argument
import com.github.ajalt.clikt.parameters.arguments.check
import com.github.ajalt.clikt.parameters.options.check
import com.github.ajalt.clikt.parameters.options.option
import com.github.ajalt.clikt.parameters.types.double
import com.github.ajalt.clikt.parameters.types.long
import ru.sug4chy.usecase.ExpenseUseCase

class UpdateCommand(
    private val expenseUseCase: ExpenseUseCase
) : CliktCommand() {

    private val id: Long by argument(help = "ID of expense, that you want to edit")
        .long()
        .check("ID must be positive.") { it > 0 }

    private val description by option(help = "New description for expense with specified ID")
    private val amount by option(help = "Amount of months")
        .double()
        .check("Amount must be positive.") { it > 0.0 }

    override fun run() {
        require(description != null || amount != null) { "You must specify description or amount to update." }

        expenseUseCase.updateExpense(id, description, amount).fold(
            onSuccess = { println("Expense updated successfully (ID: $id)") },
            onFailure = { exception ->  echoFormattedHelp(error = UsageError(exception.message)) }
        )
    }
}