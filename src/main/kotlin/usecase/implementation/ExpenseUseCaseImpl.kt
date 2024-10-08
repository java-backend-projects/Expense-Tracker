package ru.sug4chy.usecase.implementation

import ru.sug4chy.entity.Expense
import ru.sug4chy.extensions.success
import ru.sug4chy.repository.ExpenseRepository
import ru.sug4chy.usecase.ExpenseUseCase
import java.time.LocalDate
import java.time.Year

class ExpenseUseCaseImpl(
    private val expenseRepository: ExpenseRepository
) : ExpenseUseCase {

    override fun listAllExpenses(): List<Expense> =
        expenseRepository.findAll()

    override fun summary(): Double =
        expenseRepository.findAll().sumOf { it.amount }

    override fun summaryByMonth(month: Int): Double =
        expenseRepository.findAll()
            .filter { it.date.year == Year.now().value && it.date.month.value == month }
            .sumOf { it.amount }

    override fun createExpense(description: String, amount: Double): Long {
        val newExpenseId = expenseRepository.lastAddedId() + 1
        Expense(newExpenseId, LocalDate.now(), description, amount).let {
            expenseRepository.add(it)
        }

        return newExpenseId
    }

    override fun updateExpense(id: Long, description: String?, amount: Double?): Result<Unit> {
        var expense = expenseRepository.findById(id) ?: return Result.failure(Exception("Expense not found"))

        if (description != null && description != expense.description) {
            expense = expense.copy(description = description)
        }
        if (amount != null && amount != expense.amount) {
            expense = expense.copy(amount = amount)
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