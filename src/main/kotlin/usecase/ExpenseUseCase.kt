package ru.sug4chy.usecase

interface ExpenseUseCase {
    fun createExpense(description: String, amount: Double): Long
    fun updateExpense(id: Long, description: String?, amount: Double?): Result<Unit>
    fun deleteExpenseById(id: Long): Result<Unit>
}