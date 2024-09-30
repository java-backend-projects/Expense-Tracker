import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertDoesNotThrow
import org.junit.jupiter.api.assertThrows
import ru.sug4chy.repository.implementation.FileSystemExpenseRepository
import ru.sug4chy.usecase.ExpenseUseCase
import ru.sug4chy.usecase.implementation.ExpenseUseCaseImpl

class ExpenseUseCaseTests {

    private lateinit var expenseUseCase: ExpenseUseCase

    @BeforeEach
    fun setup() {
        expenseUseCase = ExpenseUseCaseImpl(
            expenseRepository = FileSystemExpenseRepository()
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

        assertDoesNotThrow {
            expenseUseCase.createExpense("desc", 1.0)
        }
    }
}