package unq.edu.remotetrainer.application.validator

import org.assertj.core.api.Assertions.assertThat
import org.joda.time.LocalDate
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import unq.edu.remotetrainer.application.rest.exception.ValidationError
import unq.edu.remotetrainer.model.Routine

internal class RoutineValidatorTest {
    private val routineValidator: RoutineValidator = RoutineValidator()

    @Test
    fun `should fail for null id`() {
        // arrange
        val routine: Routine =
            Routine(
                date = LocalDate(),
                notes = "",
                exerciseBlocks = listOf()
            )

        // act
        val error = assertThrows<ValidationError> {
            routineValidator.validateForUpdate(routine)
        }

        // assert
        assertThat(error.message).isEqualTo("Routine to update should exist in the database.")
    }

    @Test
    fun `should pass if the id is not null`() {
        val routine: Routine =
            Routine(
                id = 1,
                date = LocalDate(),
                notes = "",
                exerciseBlocks = listOf()
            )

        // act
        routineValidator.validateForUpdate(routine)

        // assert - not fails
    }
}