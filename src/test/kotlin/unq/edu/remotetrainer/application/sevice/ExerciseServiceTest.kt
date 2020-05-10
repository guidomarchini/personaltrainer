package unq.edu.remotetrainer.application.sevice

import com.nhaarman.mockitokotlin2.*
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.*
import org.junit.jupiter.api.Assertions.assertEquals
import unq.edu.remotetrainer.mapper.ExerciseMapper
import unq.edu.remotetrainer.model.Exercise
import unq.edu.remotetrainer.persistence.entity.ExerciseEntity
import unq.edu.remotetrainer.persistence.repository.ExerciseRepository
import java.util.*

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
internal class ExerciseServiceTest {

    val modelExercise: Exercise = Exercise(
        name = "sample exercise",
        description = "this one is returned by mapper"
    )
    val entityExerciseMock: ExerciseEntity = mock()

    val exerciseRepositoryMock: ExerciseRepository = mock()
    val exerciseMapperMock: ExerciseMapper = mock()

    val exerciseService: ExerciseService =
        ExerciseService(
            exerciseRepository = exerciseRepositoryMock,
            exerciseMapper = exerciseMapperMock
        )

    @BeforeEach
    fun before() {
        whenever(exerciseMapperMock.toEntity(any())).thenReturn(entityExerciseMock)
        whenever(exerciseMapperMock.toModel(entityExerciseMock)).thenReturn(modelExercise)
    }

    @AfterEach
    fun after() {
        reset(
            entityExerciseMock,
            exerciseRepositoryMock,
            exerciseMapperMock
        )
    }

    @Test
    fun `it creates an exercise`() {
        // arrange
        val exerciseMock: Exercise = sampleModelExercise()
        whenever(exerciseRepositoryMock.save(entityExerciseMock)).thenReturn(entityExerciseMock)

        // act
        exerciseService.createExercise(exerciseMock)

        // assert
        verify(exerciseMapperMock).toEntity(exerciseMock)
        verify(exerciseRepositoryMock).save(entityExerciseMock)
        verify(exerciseMapperMock).toModel(entityExerciseMock)
    }

    @Test
    fun `it gets all exercises`() {
        // arrange
        whenever(exerciseRepositoryMock.findAll())
            .thenReturn(listOf(entityExerciseMock))

        // act
        val result: List<Exercise> =
            exerciseService.getAllExercises()

        // assert
        verify(exerciseMapperMock).toModel(entityExerciseMock)
        assertEquals(1, result.size)
        assertThat(modelExercise).isEqualTo(result.first())
    }

    @Test
    fun `it gets exercise by id`() {
        // arrange
        val id: Int = 0
        whenever(exerciseRepositoryMock.getById(id))
            .thenReturn(entityExerciseMock)

        // act
        val result: Exercise? =
            exerciseService.getExerciseById(id)

        // assert
        assertThat(modelExercise).isEqualTo(result)
    }

    @Test
    fun `update - checks the exercise id is not null`() {
        assertThrows<IllegalStateException> {
            exerciseService.updateExercise(sampleModelExercise())
        }
    }

    @Test
    fun `it updates an exercise`() {
        // arrange
        val id: Int = 0
        val newName: String = "new name"
        val newDescription: String = "new description"
        val newLink: String = "new link"

        val exerciseToUpdate = Exercise(
            id = id,
            name = newName,
            description = newDescription,
            link = newLink
        )
        val entityExercise: ExerciseEntity = sampleEntityExercise()
        whenever(exerciseRepositoryMock.getAllByName(newName)).thenReturn(listOf())
        whenever(exerciseRepositoryMock.findById(id)).thenReturn(Optional.of(entityExercise))
        whenever(exerciseRepositoryMock.save(entityExercise))
            .thenReturn(entityExerciseMock)

        // act
        exerciseService.updateExercise(exerciseToUpdate)

        // assert
        verify(exerciseRepositoryMock).getAllByName(newName)
        verify(exerciseRepositoryMock).save(entityExercise)
        assertThat(newName).isEqualTo(entityExercise.name)
        assertThat(newDescription).isEqualTo(entityExercise.description)
        assertThat(newLink).isEqualTo(entityExercise.link)
    }

    @Test
    fun `it throws exception if there exists an exercise with the same name`() {
        // arrange
        val id: Int = 0
        val newName: String = "new name"
        val newDescription: String = "new description"
        val newLink: String = "new link"

        val exerciseToUpdate = Exercise(
            id = id,
            name = newName,
            description = newDescription,
            link = newLink
        )
        val existingExcercise: ExerciseEntity = ExerciseEntity(
            id = id + 1,
            name = newName,
            description = "exists with new name!"
        )
        whenever(exerciseRepositoryMock.getAllByName(newName)).thenReturn(listOf(existingExcercise))

        // act
        val error = assertThrows<java.lang.IllegalStateException> {
            exerciseService.updateExercise(exerciseToUpdate)
        }

        // assert
        assertThat(error.message).isEqualTo("Another exercise with that name exists")
    }

    @Test
    fun `it updates the exercise with the same name AND id`() {
        // arrange
        val id: Int = 0
        val newName: String = "new name"
        val anotherName: String = "another exercise name"
        val newDescription: String = "new description"
        val newLink: String = "new link"

        val exerciseToUpdate = Exercise(
            id = id,
            name = newName,
            description = newDescription,
            link = newLink
        )
        val existingExcercise: ExerciseEntity = ExerciseEntity(
            id = id + 0,
            name = anotherName,
            description = "exists with new name!"
        )
        val entityExercise: ExerciseEntity = sampleEntityExercise()

        whenever(exerciseRepositoryMock.getAllByName(newName)).thenReturn(listOf(existingExcercise))
        whenever(exerciseRepositoryMock.findById(id)).thenReturn(Optional.of(entityExercise))
        whenever(exerciseRepositoryMock.save(entityExercise))
            .thenReturn(entityExerciseMock)

        // act
        exerciseService.updateExercise(exerciseToUpdate)

        // assert
        verify(exerciseRepositoryMock).getAllByName(newName)
        verify(exerciseRepositoryMock).save(entityExercise)
    }

    @Test
    fun `it deletes an exercise`() {
        // arrange
        val id: Int = 0

        // act
        exerciseService.deleteExercise(id)

        // assert
        verify(exerciseRepositoryMock).deleteById(id)
    }



    private fun sampleModelExercise(): Exercise {
        return Exercise(
            name = "sample exercise",
            description = "sample exercise"
        )
    }

    private fun sampleEntityExercise(): ExerciseEntity {
        return ExerciseEntity(
            name = "saved exercise name",
            description = "saved exercise description",
            link = "some saved link"
        )
    }
}