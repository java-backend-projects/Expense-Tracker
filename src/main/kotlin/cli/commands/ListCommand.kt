package ru.sug4chy.cli.commands

import com.github.ajalt.clikt.core.CliktCommand
import ru.sug4chy.usecase.ExpenseUseCase
import java.time.format.DateTimeFormatter

class ListCommand(
    private val expenseUseCase: ExpenseUseCase
) : CliktCommand() {

    override fun run() {
        val expenses = expenseUseCase.listAllExpenses()

        val longestIdLength = expenses.maxOf { it.id.toString().length }.let { if (it < 2) 2 else it }
        val longestDescriptionLength = expenses.maxOf { it.description.length }.let { if (it < 11) 11 else it }

        echo(
            headers(longestIdLength, longestDescriptionLength)
        )

        expenses.forEach {
            echo(
                "${it.id.toString().padEnd(longestIdLength)} " +
                        "${it.date.format(DateTimeFormatter.ISO_LOCAL_DATE)} " +
                        "${it.description.padEnd(longestDescriptionLength)} " +
                        "\$${it.amount}"
            )
        }
    }

    private fun headers(
        longestIdLength: Int,
        longestDescriptionLength: Int
    ): String =
        "${"ID".padEnd(longestIdLength, ' ')} Date       " +
                "${"Description".padEnd(longestDescriptionLength, ' ')} Amount"
}