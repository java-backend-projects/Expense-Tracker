package ru.sug4chy.usecase.implementation

import ru.sug4chy.entity.Expense
import ru.sug4chy.extensions.success
import ru.sug4chy.repository.ExpenseRepository
import ru.sug4chy.usecase.ExpenseUseCase
import java.time.LocalDate

class ExpenseUseCaseImpl(
    private val expenseRepository: ExpenseRepository
) : ExpenseUseCase {

    override fun createExpense(description: String, amount: Double): Long {
        require(amount > 0) { "Amount must be positive" }
        require(description.isNotBlank()) { "Description cannot be blank or empty" }

        val newExpenseId = expenseRepository.lastAddedId() + 1
        Expense.create(newExpenseId, LocalDate.now(), description, amount).let {
            expenseRepository.add(it)
        }

        return newExpenseId
    }

    override fun updateExpense(id: Long, description: String?, amount: Double?): Result<Unit> {
        require(id > 0) { "ID must be positive" }
        require(amount == null || amount > 0) { "Amount must be positive" }
        require(description == null || description.isNotBlank()) { "Description cannot be blank or empty" }

        var expense = expenseRepository.findById(id) ?: return Result.failure(Exception("Expense not found"))

        if (description != null && description != expense.description) {
            expense = expense.withDescription(description)
        }
        if (amount != null && amount != expense.amount) {
            expense = expense.withAmount(amount)
        }

        expenseRepository.update(expense)

        return Result.success()
    }

    override fun deleteExpenseById(id: Long): Result<Unit> {
        if (!expenseRepository.containsById(id)) {
            return Result.failure(Exception("Expense not found"))
        }

        expenseRepository.removeById(id)

        return Result.success()
    }
}