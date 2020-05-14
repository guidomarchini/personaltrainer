package unq.edu.remotetrainer.application.validator

import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.reset
import com.nhaarman.mockitokotlin2.whenever
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import unq.edu.remotetrainer.application.rest.exception.ValidationError
import unq.edu.remotetrainer.application.sevice.ExerciseService
import unq.edu.remotetrainer.model.Exercise

class ExerciseValidatorTest {
    private val exerciseServiceMock: ExerciseService = mock()
    private val exerciseValidator: ExerciseValidator = ExerciseValidator(exerciseServiceMock)

    @AfterEach
    fun after() {
        reset(exerciseServiceMock)
    }

    @Test
    fun `should fail for null id`() {
        // arrange
        val exercise: Exercise = Exercise(
            id = null,
            name = "some name",
            description = "some description"
        )

        // act
        val error = assertThrows<ValidationError> {
            exerciseValidator.validateForUpdate(exercise)
        }

        // assert
        assertThat(error.message).isEqualTo("Exercise to update should exist in the database.")
    }

    @Test
    fun `should fail if another exercise with the same name exists`() {
        // arrange
        val exerciseName: String = "exercise name"
        val existingExerciseId: Int = 0

        val existingExercise: Exercise = Exercise(
            id = existingExerciseId,
            name = exerciseName,
            description = "some description"
        )

        whenever(exerciseServiceMock.getExerciseByName(exerciseName)).thenReturn(existingExercise)

        val exerciseToUpdate: Exercise = Exercise(
            id = existingExerciseId+1,
            name = exerciseName,
            description = "another description"
        )

        // act
        val error = assertThrows<ValidationError> {
            exerciseValidator.validateForUpdate(exerciseToUpdate)
        }

        // assert
        assertThat(error.message).isEqualTo("Another exercise with that name exists.")
    }

    @Test
    fun `should update the exercise if found exercise by name has the same id`() {
        // arrange
        val exerciseName: String = "exercise name"
        val existingExerciseId: Int = 0

        val existingExercise: Exercise = Exercise(
            id = existingExerciseId,
            name = exerciseName,
            description = "some description"
        )

        whenever(exerciseServiceMock.getExerciseByName(exerciseName)).thenReturn(existingExercise)

        val exerciseToUpdate: Exercise = Exercise(
            id = existingExerciseId,
            name = exerciseName,
            description = "another description"
        )

        // act
        exerciseValidator.validateForUpdate(exerciseToUpdate)

        // assert - shouldn't fail
    }

    @Test
    fun `should update the exercise if the name doesn't exist`() {
        // arrange
        val exerciseName: String = "exercise name"
        whenever(exerciseServiceMock.getExerciseByName(exerciseName)).thenReturn(null)

        val exerciseToUpdate: Exercise = Exercise(
            id = 0,
            name = exerciseName,
            description = "another description"
        )

        // act
        exerciseValidator.validateForUpdate(exerciseToUpdate)

        // assert - shouldn't fail
    }

    // CREATION VALIDATIONS
    @Test
    fun `create should fail if another exercise with the same name exists`() {
        // arrange
        val exerciseName: String = "exercise name"
        val existingExerciseId: Int = 0

        val existingExercise: Exercise = Exercise(
            id = existingExerciseId,
            name = exerciseName,
            description = "some description"
        )

        whenever(exerciseServiceMock.getExerciseByName(exerciseName)).thenReturn(existingExercise)

        val newExercise: Exercise = Exercise(
            name = exerciseName,
            description = "another description"
        )

        // act
        val error = assertThrows<ValidationError> {
            exerciseValidator.validateForCreate(newExercise)
        }

        // assert
        assertThat(error.message).isEqualTo("Another exercise with that name exists.")
    }

    @Test
    fun `should create the exercise if the name doesn't exist`() {
        // arrange
        val exerciseName: String = "exercise name"
        whenever(exerciseServiceMock.getExerciseByName(exerciseName)).thenReturn(null)

        val newExercise: Exercise = Exercise(
            id = 0,
            name = exerciseName,
            description = "some description"
        )

        // act
        exerciseValidator.validateForCreate(newExercise)

        // assert - shouldn't fail
    }
}