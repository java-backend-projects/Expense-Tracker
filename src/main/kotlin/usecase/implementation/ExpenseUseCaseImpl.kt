package ru.sug4chy.usecase.implementation

import ru.sug4chy.entity.Expense
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
        Expense(newExpenseId, LocalDate.now(), description, amount).let {
            expenseRepository.save(it)
        }

        return newExpenseId
    }
}