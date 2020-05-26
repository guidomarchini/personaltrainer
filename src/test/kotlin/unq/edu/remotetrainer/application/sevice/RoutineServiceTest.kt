package unq.edu.remotetrainer.application.sevice

import com.nhaarman.mockitokotlin2.mock
import org.joda.time.LocalDate
import unq.edu.remotetrainer.mapper.RoutineMapper
import unq.edu.remotetrainer.model.Routine
import unq.edu.remotetrainer.persistence.entity.RoutineEntity
import unq.edu.remotetrainer.persistence.repository.RoutineRepository

internal class RoutineServiceTest : AbstractServiceTest<Routine, RoutineEntity>() {

    override val entityObject: RoutineEntity = mock()
    override val modelObject: Routine =
        Routine(
            date = LocalDate(),
            exerciseBlocks = mutableListOf(),
            notes = "some notes"
        )

    override val repository: RoutineRepository = mock()
    override val mapper: RoutineMapper= mock()

    override val service: RoutineService =
        RoutineService(
            repository = repository,
            mapper = mapper
        )
}