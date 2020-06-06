package unq.edu.remotetrainer.application.sevice

import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import org.assertj.core.api.Assertions.assertThat
import org.joda.time.LocalDate
import org.junit.jupiter.api.Test
import unq.edu.remotetrainer.mapper.RoutineMapper
import unq.edu.remotetrainer.model.Routine
import unq.edu.remotetrainer.persistence.entity.RoutineEntity
import unq.edu.remotetrainer.persistence.repository.RoutineRepository

internal class RoutineServiceTest : AbstractServiceTest<Routine, RoutineEntity>() {

    override val entityObject: RoutineEntity = mock()
    override val modelObject: Routine =
        Routine(
            date = LocalDate(),
            shortDescription = "test routine",
            exerciseBlocks = mutableListOf(),
            notes = "some notes"
        )

    override val repository: RoutineRepository = mock()
    override val mapper: RoutineMapper = mock()
    val exerciseBlockServiceMock: ExerciseBlockService = mock()

    override val service: RoutineService =
        RoutineService(
            repository = repository,
            mapper = mapper,
            exerciseBlockService = exerciseBlockServiceMock
        )

    @Test
    fun `it searches routines starting from the received date, up to the received date`() {
        // arrange
        val startingDate: LocalDate = LocalDate()
        val endingDate: LocalDate = startingDate.plusDays(6)

        whenever(repository.getAllByDateBetween(startingDate, endingDate))
            .thenReturn(listOf(entityObject))

        // act
        val result: List<Routine> =
            service.getRoutines(
                startingDate = startingDate,
                endingDate = endingDate
            ).toList()

        // assert
        assertThat(result).hasSize(1)
        verify(repository).getAllByDateBetween(startingDate, endingDate)
    }

    // TODO processRoutineWeek(), create(), update()
}