package ru.sug4chy.entity

import java.time.LocalDate

class Expense(
    val id: Long,
    val date: LocalDate,
    val description: String,
    val amount: Double,
)