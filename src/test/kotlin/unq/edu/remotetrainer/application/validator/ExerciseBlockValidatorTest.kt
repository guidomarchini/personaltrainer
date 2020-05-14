package unq.edu.remotetrainer.application.validator

import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.reset
import com.nhaarman.mockitokotlin2.whenever
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import unq.edu.remotetrainer.application.rest.exception.ValidationError
import unq.edu.remotetrainer.application.sevice.ExerciseBlockService
import unq.edu.remotetrainer.model.ExerciseBlock

internal class ExerciseBlockValidatorTest {
    private val exerciseBlockServiceMock: ExerciseBlockService = mock()
    private val exerciseBlockValidator: ExerciseBlockValidator = ExerciseBlockValidator(exerciseBlockServiceMock)

    @AfterEach
    fun after() {
        reset(exerciseBlockServiceMock)
    }

    // UPDATE VALIDATIONS
    @Test
    fun `should fail for null id`() {
        // arrange
        val exerciseBlock: ExerciseBlock = ExerciseBlock(
            id = null,
            name = "some name",
            exercises = listOf(),
            notes = ""
        )

        // act
        val error = assertThrows<ValidationError> {
            exerciseBlockValidator.validateForUpdate(exerciseBlock)
        }

        // assert
        assertThat(error.message).isEqualTo("ExerciseBlock to update should exist in the database.")
    }

    @Test
    fun `should fail if another exercise block with the same name exists`() {
        // arrange
        val exerciseBlockName: String = "exercise block name"
        val existingExerciseBlockId: Int = 0

        val existingExerciseBlock: ExerciseBlock = ExerciseBlock(
            id = existingExerciseBlockId,
            name = exerciseBlockName,
            exercises = listOf(),
            notes = ""
        )

        whenever(exerciseBlockServiceMock.getAllNamedBlocks()).thenReturn(listOf(existingExerciseBlock))

        val exerciseBlockToUpdate: ExerciseBlock = ExerciseBlock(
            id = existingExerciseBlockId+1,
            name = exerciseBlockName,
            exercises = listOf(),
            notes = ""
        )

        // act
        val error = assertThrows<ValidationError> {
            exerciseBlockValidator.validateForUpdate(exerciseBlockToUpdate)
        }

        // assert
        assertThat(error.message).isEqualTo("Another exercise block with that name exists.")
    }

    @Test
    fun `should update the exercise if found exercise block by name has the same id`() {
        // arrange
        val exerciseBlockName: String = "exercise block name"
        val existingExerciseBlockId: Int = 0

        val existingExerciseBlock: ExerciseBlock = ExerciseBlock(
            id = existingExerciseBlockId,
            name = exerciseBlockName,
            exercises = listOf(),
            notes = ""
        )

        whenever(exerciseBlockServiceMock.getAllNamedBlocks()).thenReturn(listOf(existingExerciseBlock))

        val exerciseBlockToUpdate: ExerciseBlock = ExerciseBlock(
            id = existingExerciseBlockId,
            name = exerciseBlockName,
            exercises = listOf(),
            notes = ""
        )

        // act
        exerciseBlockValidator.validateForUpdate(exerciseBlockToUpdate)

        // assert - shouldn't fail
    }

    @Test
    fun `should update the exercise if the name doesn't exist`() {
        // arrange
        val exerciseBlockName: String = "exercise block name"

        whenever(exerciseBlockServiceMock.getAllNamedBlocks()).thenReturn(listOf())

        val exerciseBlockToUpdate: ExerciseBlock = ExerciseBlock(
            id = 0,
            name = exerciseBlockName,
            exercises = listOf(),
            notes = ""
        )

        // act
        exerciseBlockValidator.validateForUpdate(exerciseBlockToUpdate)

        // assert - shouldn't fail
    }

    // CREATION VALIDATIONS
    @Test
    fun `create should fail if another exercise with the same name exists`() {
        // arrange
        val exerciseBlockName: String = "exercise block name"
        val existingExerciseBlockId: Int = 0

        val existingExerciseBlock: ExerciseBlock = ExerciseBlock(
            id = existingExerciseBlockId,
            name = exerciseBlockName,
            exercises = listOf(),
            notes = ""
        )

        whenever(exerciseBlockServiceMock.getAllNamedBlocks()).thenReturn(listOf(existingExerciseBlock))

        val newExerciseBlock: ExerciseBlock = ExerciseBlock(
            name = exerciseBlockName,
            exercises = listOf(),
            notes = ""
        )

        // act
        val error = assertThrows<ValidationError> {
            exerciseBlockValidator.validateForCreate(newExerciseBlock)
        }

        // assert
        assertThat(error.message).isEqualTo("Another exercise block with that name exists.")
    }

    @Test
    fun `should create the exercise if the name doesn't exist`() {
        // arrange
        val exerciseBlockName: String = "exercise block name"
        whenever(exerciseBlockServiceMock.getAllNamedBlocks()).thenReturn(listOf())

        val newExerciseBlock: ExerciseBlock = ExerciseBlock(
            name = exerciseBlockName,
            exercises = listOf(),
            notes = ""
        )

        // act
        exerciseBlockValidator.validateForCreate(newExerciseBlock)

        // assert - shouldn't fail
    }
}