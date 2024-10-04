package ru.sug4chy.cli.commands

import com.github.ajalt.clikt.parameters.arguments.argument
import com.github.ajalt.clikt.parameters.arguments.check
import com.github.ajalt.clikt.parameters.options.check
import com.github.ajalt.clikt.parameters.options.option
import com.github.ajalt.clikt.parameters.types.double
import com.github.ajalt.clikt.parameters.types.long
import ru.sug4chy.cli.commands.abstractions.BaseCliCommand
import ru.sug4chy.usecase.ExpenseUseCase

class UpdateCommand(
    private val expenseUseCase: ExpenseUseCase
) : BaseCliCommand(
    help = "Edits description or/and amount of the expense with the specified ID.",
    name = "update",
) {

    private val id: Long by argument(help = "ID of expense, that you want to edit")
        .long()
        .check("ID must be positive.") { it > 0 }

    private val description by option(help = "New description for expense with specified ID")
        .check("Description cannot be blank or empty") { it.isNotBlank() }

    private val amount by option(help = "Amount of months")
        .double()
        .check("Amount must be positive.") { it > 0.0 }

    override fun run() {
        require(description != null || amount != null) { "You must specify description or amount to update." }

        val result = expenseUseCase.updateExpense(id, description, amount)

        result.fold(
            onSuccess = { println("Expense updated successfully (ID: $id)") },
            onFailure = { throw it }
        )
    }
}