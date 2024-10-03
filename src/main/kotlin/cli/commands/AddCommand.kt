package ru.sug4chy.cli.commands

import com.github.ajalt.clikt.core.CliktCommand
import com.github.ajalt.clikt.parameters.options.check
import com.github.ajalt.clikt.parameters.options.option
import com.github.ajalt.clikt.parameters.options.required
import com.github.ajalt.clikt.parameters.types.double
import ru.sug4chy.usecase.ExpenseUseCase

class AddCommand(
    private val expenseUseCase: ExpenseUseCase
) : CliktCommand() {

    private val description: String by option(help = "Description of new expense")
        .required()

    private val amount: Double by option(help = "Amount of new expense")
        .double()
        .required()
        .check("Amount must be positive.") { it > 0.0 }

    override fun run() {
        expenseUseCase.createExpense(description, amount).also {
            echo("Expense added successfully (ID: $it)")
        }
    }
}