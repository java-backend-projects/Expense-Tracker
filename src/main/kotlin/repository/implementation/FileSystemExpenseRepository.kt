package ru.sug4chy.repository.implementation

import ru.sug4chy.entity.Expense
import ru.sug4chy.extensions.withoutHeaders
import ru.sug4chy.repository.ExpenseRepository
import java.io.File
import java.io.IOException

class FileSystemExpenseRepository : ExpenseRepository {

    override fun lastAddedId(): Long {
        ensureFileCreated(createIfNotExists = true)
        val file = File(EXPENSES_CSV_FILE_PATH)

        val lastAddedId = file.useLines { lines ->
            lines
                .withoutHeaders()
                .map { line ->
                    line.split(",")[0].toLongOrNull()
                }
                .filterNotNull()
                .maxOrNull()
        }

        return lastAddedId ?: 0
    }

    override fun findById(id: Long): Expense? {
        ensureFileCreated()

        return File(EXPENSES_CSV_FILE_PATH).useLines { lines ->
            lines
                .withoutHeaders()
                .map { line ->
                    Expense.fromCsvString(line)
                }
                .firstOrNull { expense -> expense.id == id }
        }
    }

    override fun add(expense: Expense) {
        ensureFileCreated(createIfNotExists = true)
        File(EXPENSES_CSV_FILE_PATH).appendText(expense.toCsvString())
    }

    @Throws(IOException::class)
    override fun update(expense: Expense) {
        ensureFileCreated()
        val file = File(EXPENSES_CSV_FILE_PATH)
        val tempFile = File("$EXPENSES_CSV_FILE_PATH.tmp").also {
            it.createNewFile()
        }

        file.forEachLine { line ->
            if (line.startsWith("${expense.id},")) {
                tempFile.appendText(expense.toCsvString())
            } else {
                tempFile.appendText("$line\n")
            }
        }

        file.delete()
        tempFile.renameTo(file)
    }

    companion object {
        private val EXPENSES_CSV_FILE_PATH: String = "${System.getProperty("user.dir")}/expenses.csv"

        @Throws(IOException::class, NoSuchFileException::class)
        private fun ensureFileCreated(createIfNotExists: Boolean = false) =
            with(File(EXPENSES_CSV_FILE_PATH)) {
                if (this.exists()) {
                    return
                }

                if (!createIfNotExists) {
                    throw NoSuchFileException(this)
                }

                this.createNewFile()
                this.writeText("id,date,description,amount\n")
            }
    }
}