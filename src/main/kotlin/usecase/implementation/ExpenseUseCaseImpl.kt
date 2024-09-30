package ru.sug4chy.usecase.implementation

import ru.sug4chy.repository.ExpenseRepository
import ru.sug4chy.usecase.ExpenseUseCase

class ExpenseUseCaseImpl(
    private val expenseRepository: ExpenseRepository
) : ExpenseUseCase {

    override fun createExpense(description: String, amount: Double): Result<Long> {
        require(amount > 0) { "Amount must be positive" }
        require(description.isNotBlank()) { "Description cannot be blank or empty" }

        val lastAddedId = expenseRepository.lastAddedId().getOrElse {
            return Result.failure(it)
        }

        return Result.success(0L)
    }
}