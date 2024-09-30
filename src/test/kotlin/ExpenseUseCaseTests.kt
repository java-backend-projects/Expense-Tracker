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
import kotlin.properties.Delegates
import kotlin.test.assertEquals

class ExpenseUseCaseTests {

    private lateinit var expenseUseCase: ExpenseUseCase
    private lateinit var expenseRepository: ExpenseRepository

    @BeforeEach
    fun setup() {
        expenseRepository = mockk<ExpenseRepository>()

        expenseUseCase = ExpenseUseCaseImpl(
            expenseRepository = expenseRepository
        )
    }

    @Test
    fun `Check that createExpense() requirements for parameter amount`() {
        assertThrows<IllegalArgumentException> {
            expenseUseCase.createExpense("d", -1.0)
        }

        assertThrows<IllegalArgumentException> {
            expenseUseCase.createExpense("d", 0.0)
        }

        every { expenseRepository.lastAddedId() } returns 0
        every { expenseRepository.save(any<Expense>()) } returns Unit

        assertDoesNotThrow {
            expenseUseCase.createExpense("d", 1.0)
        }
    }

    @Test
    fun `Check createExpense() requirements for parameter description`() {
        assertThrows<IllegalArgumentException> {
            expenseUseCase.createExpense("", 1.0)
        }

        assertThrows<IllegalArgumentException> {
            expenseUseCase.createExpense("    ", 1.0)
        }

        every { expenseRepository.lastAddedId() } returns 0
        every { expenseRepository.save(any<Expense>()) } returns Unit

        assertDoesNotThrow {
            expenseUseCase.createExpense("desc", 1.0)
        }
    }

    @Test
    fun `Check that createExpense() works correctly`() {
        every { expenseRepository.lastAddedId() } returns 0
        every { expenseRepository.save(any<Expense>()) } returns Unit

        var id: Long by Delegates.notNull()
        assertDoesNotThrow {
            id = expenseUseCase.createExpense("desc", 1.0)
        }

        assertEquals(id, 1L)

        verify { expenseRepository.lastAddedId() }
        verify { expenseRepository.save(Expense(1L, LocalDate.now(), "desc", 1.0)) }
    }
}