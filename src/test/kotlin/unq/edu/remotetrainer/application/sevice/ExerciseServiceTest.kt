package unq.edu.remotetrainer.application.sevice

import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.reset
import com.nhaarman.mockitokotlin2.whenever
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Test
import unq.edu.remotetrainer.mapper.ExerciseMapper
import unq.edu.remotetrainer.model.Exercise
import unq.edu.remotetrainer.persistence.entity.ExerciseEntity
import unq.edu.remotetrainer.persistence.repository.ExerciseRepository

internal class ExerciseServiceTest: AbstractServiceTest<Exercise, ExerciseEntity>() {

    override val modelObject: Exercise = Exercise(
        name = "sample exercise",
        description = "this one is returned by mapper"
    )
    override val entityObject: ExerciseEntity = mock()

    override val repository: ExerciseRepository = mock()
    override val mapper: ExerciseMapper = mock()

    override val service: ExerciseService =
        ExerciseService(
            repository = repository,
            mapper = mapper
        )

    @AfterEach
    fun after() {
        reset(
            entityObject,
            repository,
            mapper
        )
    }

    @Test
    fun `it gets exercises by name`() {
        // arrange
        val name: String = "some exercise"
        whenever(repository.getByName(name))
            .thenReturn(entityObject)

        // act
        val result: Exercise? =
            service.getExerciseByName(name)

        // assert
        assertThat(result).isEqualTo(modelObject)
    }
}