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

class ExpenseUseCaseCreateExpenseTests {

    private lateinit var expenseUseCase: ExpenseUseCase
    private lateinit var expenseRepository: ExpenseRepository

    @BeforeEach
    fun setup() {
        expenseRepository = mockk()

        expenseUseCase = ExpenseUseCaseImpl(
            expenseRepository = expenseRepository
        )
    }

    @Test
    fun `Check requirements for parameter 'amount'`() {
        assertThrows<IllegalArgumentException> {
            expenseUseCase.createExpense("d", -1.0)
        }

        assertThrows<IllegalArgumentException> {
            expenseUseCase.createExpense("d", 0.0)
        }

        every { expenseRepository.lastAddedId() } returns 0
        every { expenseRepository.add(any<Expense>()) } returns Unit

        assertDoesNotThrow {
            expenseUseCase.createExpense("d", 1.0)
        }
    }

    @Test
    fun `Check requirements for parameter 'description'`() {
        assertThrows<IllegalArgumentException> {
            expenseUseCase.createExpense("", 1.0)
        }

        assertThrows<IllegalArgumentException> {
            expenseUseCase.createExpense("    ", 1.0)
        }

        every { expenseRepository.lastAddedId() } returns 0
        every { expenseRepository.add(any<Expense>()) } returns Unit

        assertDoesNotThrow {
            expenseUseCase.createExpense("desc", 1.0)
        }
    }

    @Test
    fun `Check that repository methods were called`() {
        every { expenseRepository.lastAddedId() } returns 0
        every { expenseRepository.add(any()) } returns Unit

        expenseUseCase.createExpense("desc", 1.0)

        verify { expenseRepository.lastAddedId() }
        verify { expenseRepository.add(Expense.create(1L, LocalDate.now(), "desc", 1.0)) }
    }

    @Test
    fun `Check returned value`() {
        every { expenseRepository.lastAddedId() } returns 0
        every { expenseRepository.add(any()) } returns Unit

        val id = expenseUseCase.createExpense("desc", 1.0)

        assertEquals(
            expected = 1L,
            actual = id
        )
    }
}