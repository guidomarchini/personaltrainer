package unq.edu.remotetrainer.application.validator

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import unq.edu.remotetrainer.application.rest.exception.ValidationError
import unq.edu.remotetrainer.model.ExerciseBlock

internal class ImportValidatorTest {
    private val importValidator: ImportValidator = ImportValidator()

    @Test
    fun `should fail if an exercise block has null name`() {
        // arrange
        val namedExerciseBlock: ExerciseBlock =
            ExerciseBlock(
                name = "I have a name!",
                notes = "",
                exercises = listOf()
            )

        val unnamedExerciseBlock: ExerciseBlock =
            ExerciseBlock(
                name = null,
                notes = "",
                exercises = listOf()
            )

        // act
        val error = assertThrows<ValidationError> {
            importValidator.validateExerciseBlocks(listOf(namedExerciseBlock, unnamedExerciseBlock))
        }

        // assert
        assertThat(error.message).isEqualTo("All exercise blocks should have a name")
    }

    @Test
    fun `should pass if every exercise block has a name`() {
        // arrange
        val namedExerciseBlock: ExerciseBlock =
            ExerciseBlock(
                name = "I have a name!",
                notes = "",
                exercises = listOf()
            )

        val anotherNamedBlock: ExerciseBlock =
            ExerciseBlock(
                name = "I have a name, too!",
                notes = "",
                exercises = listOf()
            )

        // act
        importValidator.validateExerciseBlocks(listOf(namedExerciseBlock, anotherNamedBlock))

        // assert - not fails
    }
}