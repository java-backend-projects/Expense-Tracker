package ru.sug4chy.cli

import com.github.ajalt.clikt.core.CliktCommand
import com.github.ajalt.clikt.core.Context

class ExpenseTracker : CliktCommand(
    name = "expense-tracker"
) {

    override fun help(context: Context): String =
        "CLI application for tracking your expenses."

    override fun run() = Unit
}