package expenseusecase

import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertDoesNotThrow
import org.junit.jupiter.api.assertThrows
import ru.sug4chy.entity.Expense
import ru.sug4chy.repository.ExpenseRepository
import ru.sug4chy.usecase.ExpenseUseCase
import ru.sug4chy.usecase.implementation.ExpenseUseCaseImpl
import java.time.LocalDate
import kotlin.test.assertEquals

class ExpenseUseCaseUpdateTests {

    private lateinit var expenseRepository: ExpenseRepository
    private lateinit var expenseUseCase: ExpenseUseCase

    @BeforeEach
    fun setup() {
        expenseRepository = mockk()
        expenseUseCase = ExpenseUseCaseImpl(expenseRepository)
    }

    @Test
    fun `Check requirements for 'description' parameter`() {
        assertThrows<IllegalArgumentException> {
            expenseUseCase.updateExpense(0, "", 1.0)
            expenseUseCase.updateExpense(0, "   ", 1.0)
        }

        every { expenseRepository.update(any()) } returns Unit
        every { expenseRepository.findById(any()) } returns null

        assertDoesNotThrow {
            expenseUseCase.updateExpense(0, null, 1.0)
            expenseUseCase.updateExpense(0, "desc", 1.0)
        }
    }

    @Test
    fun `Check requirements for 'amount' parameter`() {
        assertThrows<IllegalArgumentException> {
            expenseUseCase.updateExpense(0, "d", -1.0)
            expenseUseCase.updateExpense(0, "d", 0.0)
        }

        every { expenseRepository.update(any()) } returns Unit
        every { expenseRepository.findById(any()) } returns null

        assertDoesNotThrow {
            expenseUseCase.updateExpense(0, "d", null)
            expenseUseCase.updateExpense(0, "d", 10.0)
        }
    }

    @Test
    fun `Check that repository methods were called`() {
        // Check 'Expense not found' case
        every { expenseRepository.findById(0) } returns null
        every { expenseRepository.update(any()) } returns Unit

        expenseUseCase.updateExpense(0, "d", 1.0)

        verify { expenseRepository.findById(0) }
        verify(exactly = 0) { expenseRepository.update(any()) }

        // Check 'edit description and amount' case
        every { expenseRepository.findById(1) } returns
                Expense.create(1, LocalDate.now(), "ddd", 20.0)

        expenseUseCase.updateExpense(1, "d", 10.0)

        verify { expenseRepository.findById(1) }
        verify { expenseRepository.update(Expense.create(1, LocalDate.now(), "d", 10.0)) }

        // Check 'edit only description' case
        expenseUseCase.updateExpense(1, "d", null)

        verify { expenseRepository.findById(1) }
        verify { expenseRepository.update(Expense.create(1, LocalDate.now(), "d", 20.0)) }

        // Check 'edit only amount' case
        expenseUseCase.updateExpense(1, null, 1.0)

        verify { expenseRepository.findById(1) }
        verify { expenseRepository.update(Expense.create(1, LocalDate.now(), "ddd", 1.0)) }
    }

    @Test
    fun `Check returned value`() {
        every { expenseRepository.findById(0) } returns null
        every { expenseRepository.update(any()) } returns Unit

        val failureResult = expenseUseCase.updateExpense(0, "desc", 1.0)
        assertEquals(
            expected = "Expense not found",
            actual = failureResult.exceptionOrNull()?.message
        )

        every { expenseRepository.findById(1) } returns
                Expense.create(1, LocalDate.now(), "ddd", 20.0)

        val successResult = expenseUseCase.updateExpense(1, "desc", 1.0)
        assertEquals(
            expected = Unit,
            actual = successResult.getOrDefault(Unit)
        )
    }
}