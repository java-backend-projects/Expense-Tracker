package ru.sug4chy.cli

import ru.sug4chy.cli.commands.abstractions.BaseCliCommand

class ExpenseTracker : BaseCliCommand(
    help = "CLI application for tracking your expenses.",
    name = "expense-tracker"
) {
    override fun run() = Unit
}