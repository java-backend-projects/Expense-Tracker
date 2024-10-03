package ru.sug4chy.repository

import ru.sug4chy.entity.Expense

interface ExpenseRepository {
    fun lastAddedId(): Long
    fun containsById(id: Long): Boolean
    fun findById(id: Long): Expense?
    fun add(expense: Expense)
    fun update(expense: Expense)
    fun removeById(id: Long)
}