package ru.sug4chy.entity

import java.time.LocalDate
import java.time.format.DateTimeFormatter

data class Expense(
    val id: Long,
    val date: LocalDate,
    val description: String,
    val amount: Double,
) {
    fun toCsvString(): String =
        "$id,${date.format(DateTimeFormatter.ISO_LOCAL_DATE)},$description,$amount\n"

    companion object {
        fun fromCsvString(csvString: String): Expense =
            with(csvString.split(",")) {
                Expense(
                    id = this[0].toLong(),
                    date = LocalDate.parse(this[1]),
                    description = this[2],
                    amount = this[3].removeSuffix("\n").toDouble()
                )
            }
    }
}