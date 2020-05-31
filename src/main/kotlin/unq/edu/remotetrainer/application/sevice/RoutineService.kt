package unq.edu.remotetrainer.application.sevice

import org.joda.time.LocalDate
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import unq.edu.remotetrainer.mapper.RoutineMapper
import unq.edu.remotetrainer.model.Routine
import unq.edu.remotetrainer.persistence.entity.RoutineEntity
import unq.edu.remotetrainer.persistence.repository.RoutineRepository

@Service
class RoutineService @Autowired constructor(
    override val repository: RoutineRepository,
    override val mapper: RoutineMapper
): RemoteTrainerService<Routine, RoutineEntity> {

    /**
     * Returns the routines for the given dates.
     * Both dates are included in the routines.
     */
    fun getRoutines(
        startingDate: LocalDate,
        endingDate: LocalDate
    ): Iterable<Routine> {
        return repository.getAllByDateBetween(
            from = startingDate,
            to = endingDate
        ).map { mapper.toModel(it) }
    }
}