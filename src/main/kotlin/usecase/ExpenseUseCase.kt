package ru.sug4chy.usecase

interface ExpenseUseCase {
    fun createExpense(description: String, amount: Double): Result<Long>
}