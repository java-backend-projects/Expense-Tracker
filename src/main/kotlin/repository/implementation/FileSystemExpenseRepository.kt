package ru.sug4chy.repository.implementation

import ru.sug4chy.entity.Expense
import ru.sug4chy.repository.ExpenseRepository
import java.io.File
import java.io.IOException
import java.time.format.DateTimeFormatter

class FileSystemExpenseRepository : ExpenseRepository {

    override fun lastAddedId(): Long {
        val file = ensureFileCreated()

        val lastAddedId = file.useLines { lines ->
            lines
                .drop(1)
                .map { line ->
                    line.split(",")[0].toLongOrNull()
                }
                .filterNotNull()
                .maxOrNull()
        }

        return lastAddedId ?: 0
    }

    override fun save(expense: Expense) =
        ensureFileCreated().appendText(
            "${expense.id},${expense.date.format(DateTimeFormatter.ISO_LOCAL_DATE)}," +
                    "${expense.description},${expense.amount}\n"
        )

    companion object {
        private val EXPENSES_CSV_FILE_PATH: String = "${System.getProperty("user.dir")}/expenses.csv"

        @Throws(IOException::class, NoSuchFileException::class)
        private fun ensureFileCreated(createIfNotExists: Boolean = true) =
            File(EXPENSES_CSV_FILE_PATH).apply {
                if (this.exists()) {
                    return@apply
                }

                if (!createIfNotExists) {
                    throw NoSuchFileException(this)
                }

                this.createNewFile()
                this.writeText("id,date,description,amount\n")
            }
    }
}