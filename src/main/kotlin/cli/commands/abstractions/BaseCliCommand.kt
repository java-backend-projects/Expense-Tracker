package ru.sug4chy.cli.commands.abstractions

import com.github.ajalt.clikt.core.CliktCommand
import com.github.ajalt.clikt.core.Context

abstract class BaseCliCommand(
    private val help: String,
    name: String
) : CliktCommand(name = name) {

    override fun help(context: Context): String = help

}