package ru.sug4chy.repository

import ru.sug4chy.entity.Expense

interface ExpenseRepository {
    fun lastAddedId(): Long
    fun findById(id: Long): Expense?
    fun add(expense: Expense)
    fun update(expense: Expense)
}