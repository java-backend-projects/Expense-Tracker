package ru.sug4chy.entity

import java.time.LocalDate
import java.time.format.DateTimeFormatter

class Expense private constructor(
    val id: Long,
    val date: LocalDate,
    val description: String,
    val amount: Double,
) {
    fun toCsvString(): String =
        "$id,${date.format(DateTimeFormatter.ISO_LOCAL_DATE)},$description,$amount\n"

    fun withDescription(description: String): Expense =
        Expense(this.id, this.date, description, this.amount)

    fun withAmount(amount: Double): Expense =
        Expense(this.id, this.date, this.description, amount)

    override operator fun equals(other: Any?): Boolean {
        if (other == null || other !is Expense) return false
        if (this === other) return true

        return id == other.id
    }

    override fun hashCode(): Int {
        var result = id.hashCode()
        result = 31 * result + date.hashCode()
        result = 31 * result + description.hashCode()
        result = 31 * result + amount.hashCode()
        return result
    }

    companion object {
        fun create(id: Long, date: LocalDate, description: String, amount: Double): Expense =
            Expense(id, date, description, amount)

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