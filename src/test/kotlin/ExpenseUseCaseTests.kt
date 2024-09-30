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
    fun `Check that createExpense() requirements work`() {
        assertThrows<IllegalArgumentException> {
            expenseUseCase.createExpense("", -1.0)
        }

        assertThrows<IllegalArgumentException> {
            expenseUseCase.createExpense("desc", -1.0)
        }

        assertThrows<IllegalArgumentException> {
            expenseUseCase.createExpense("", 10.0)
        }

        assertDoesNotThrow {
            expenseUseCase.createExpense("desc", 10.0)
        }
    }
}