package unq.edu.remotetrainer.application.sevice

import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import unq.edu.remotetrainer.mapper.ExerciseBlockMapper
import unq.edu.remotetrainer.model.ExerciseBlock
import unq.edu.remotetrainer.persistence.entity.ExerciseBlockEntity
import unq.edu.remotetrainer.persistence.repository.ExerciseBlockRepository

internal class ExerciseBlockServiceTest: AbstractServiceTest<ExerciseBlock, ExerciseBlockEntity>() {

    override val entityObject: ExerciseBlockEntity = mock()

    override val modelObject: ExerciseBlock = ExerciseBlock(
        name = "sample exercise block",
        exercises = listOf(),
        notes = "some notes"
    )

    override val repository: ExerciseBlockRepository = mock()
    override val mapper: ExerciseBlockMapper = mock()

    override val service: ExerciseBlockService =
        ExerciseBlockService(
            repository = repository,
            mapper = mapper
        )


    @Test
    fun `it gets only the blocks with names`() {
        // arrange
        whenever(repository.getAllByNameNotNull())
            .thenReturn(listOf(entityObject))

        // act
        val result: List<ExerciseBlock> =
            service.getAllNamedBlocks()

        // assert
        assertThat(result.size).isEqualTo(1)
        assertThat(result).contains(modelObject) // mapper.toModel(entity) returns modelObject
        verify(mapper).toModel(entityObject)
    }


}