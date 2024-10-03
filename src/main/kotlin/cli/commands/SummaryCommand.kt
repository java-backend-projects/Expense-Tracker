package ru.sug4chy.cli.commands

import com.github.ajalt.clikt.core.CliktCommand
import com.github.ajalt.clikt.parameters.options.check
import com.github.ajalt.clikt.parameters.options.option
import com.github.ajalt.clikt.parameters.types.int
import ru.sug4chy.usecase.ExpenseUseCase

class SummaryCommand(
    private val expenseUseCase: ExpenseUseCase
) : CliktCommand() {

    private val month by option().int()
        .check("Month must be between 1 and 12.") { it in 1..12 }

    override fun run() =
        echo("Total expenses: \$" +
                "${if (month != null) expenseUseCase.summaryByMonth(month!!) else expenseUseCase.summary()}")
}