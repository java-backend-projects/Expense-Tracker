package ru.sug4chy.repository.implementation

import ru.sug4chy.entity.Expense
import ru.sug4chy.extensions.withoutHeaders
import ru.sug4chy.repository.ExpenseRepository
import java.io.File
import java.io.IOException

class FileSystemExpenseRepository : ExpenseRepository {

    override fun lastAddedId(): Long {
        ensureFileCreated()
        val file = File(EXPENSES_CSV_FILE_PATH)

        val lastAddedId = file.useLines { lines ->
            lines
                .withoutHeaders()
                .map { line -> line.split(",")[0].toLongOrNull() }
                .filterNotNull()
                .maxOrNull()
        }

        return lastAddedId ?: 0
    }

    override fun containsById(id: Long): Boolean {
        val file = File(EXPENSES_CSV_FILE_PATH)

        return file.useLines { lines ->
            lines
                .withoutHeaders()
                .map { line -> Expense.fromCsvString(line) }
                .firstOrNull { e -> e.id == id }
        } != null
    }

    override fun findById(id: Long): Expense? =
        File(EXPENSES_CSV_FILE_PATH).useLines { lines ->
            lines
                .withoutHeaders()
                .map { line -> Expense.fromCsvString(line) }
                .firstOrNull { expense -> expense.id == id }
        }

    override fun findAll(): List<Expense> =
        File(EXPENSES_CSV_FILE_PATH).useLines { lines ->
            lines
                .withoutHeaders()
                .map { line -> Expense.fromCsvString(line) }
                .toList()
        }

    override fun add(expense: Expense) {
        ensureFileCreated()
        File(EXPENSES_CSV_FILE_PATH).appendText(expense.toCsvString())
    }

    override fun update(expense: Expense) =
        rewriteFileWithApplying { file ->
            return@rewriteFileWithApplying { line ->
                if (line.startsWith("${expense.id},")) {
                    file.appendText(expense.toCsvString())
                } else {
                    file.appendText("$line\n")
                }
            }
        }

    override fun removeById(id: Long) =
        rewriteFileWithApplying { file ->
            return@rewriteFileWithApplying { line ->
                if (!line.startsWith("$id,")) {
                    file.appendText("$line\n")
                }
            }
        }

    @Throws(IOException::class)
    private fun rewriteFileWithApplying(lineAction: (File) -> (String) -> Unit) {
        val file = File(EXPENSES_CSV_FILE_PATH)
        val tempFile = File("$EXPENSES_CSV_FILE_PATH.tmp").also {
            it.createNewFile()
        }

        file.forEachLine(action = lineAction(tempFile))

        file.delete()
        tempFile.renameTo(file)
    }

    companion object {
        private val EXPENSES_CSV_FILE_PATH: String = "${System.getProperty("user.dir")}/expenses.csv"

        @Throws(IOException::class)
        private fun ensureFileCreated() =
            with(File(EXPENSES_CSV_FILE_PATH)) {
                if (this.exists()) {
                    return
                }

                this.createNewFile()
                this.writeText("id,date,description,amount\n")
            }
    }
}