package ru.sug4chy.repository

import ru.sug4chy.entity.Expense

interface ExpenseRepository {
    fun lastAddedId(): Long
    fun save(expense: Expense)
}