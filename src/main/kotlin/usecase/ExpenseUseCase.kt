package ru.sug4chy.usecase

import ru.sug4chy.entity.Expense

interface ExpenseUseCase {
    fun listAllExpenses(): List<Expense>
    fun summary(): Double
    fun summaryByMonth(month: Int): Double
    fun createExpense(description: String, amount: Double): Long
    fun updateExpense(id: Long, description: String?, amount: Double?): Result<Unit>
    fun deleteExpenseById(id: Long): Result<Unit>
}