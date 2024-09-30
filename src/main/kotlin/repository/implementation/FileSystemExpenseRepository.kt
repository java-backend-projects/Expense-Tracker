package ru.sug4chy.repository.implementation

import ru.sug4chy.repository.ExpenseRepository
import java.io.File
import java.io.IOException

class FileSystemExpenseRepository : ExpenseRepository {

    override fun lastAddedId(): Result<Long> {
        val file = ensureFileCreated(createIfNotExists = true)

        val lastAddedId = file.useLines { lines ->
            lines
                .drop(1)
                .map { line ->
                    line.split(";")[0].toLongOrNull()
                }
                .filterNotNull()
                .maxOrNull()
        }

        return if (lastAddedId != null)
            Result.success(lastAddedId)
        else
            Result.failure(IndexOutOfBoundsException("File contains no expenses"))
    }

    override fun save(): Result<Unit> {
        TODO("Not yet implemented")
    }

    companion object {
        private val EXPENSES_CSV_FILE_PATH: String = "${System.getProperty("user.dir")}/expenses.csv"

        @Throws(IOException::class, NoSuchFileException::class)
        private fun ensureFileCreated(createIfNotExists: Boolean) =
            File(EXPENSES_CSV_FILE_PATH).apply {
                if (this.exists()) {
                    return@apply
                }

                if (!createIfNotExists) {
                    throw NoSuchFileException(this)
                }

                this.createNewFile()
                this.writeText("id;date;description;amount")
            }
    }
}