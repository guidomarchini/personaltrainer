package unq.edu.remotetrainer.application.sevice

import org.joda.time.LocalDate
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import unq.edu.remotetrainer.application.dto.RoutineDay
import unq.edu.remotetrainer.mapper.RoutineMapper
import unq.edu.remotetrainer.model.ExerciseBlock
import unq.edu.remotetrainer.model.Routine
import unq.edu.remotetrainer.persistence.entity.RoutineEntity
import unq.edu.remotetrainer.persistence.repository.RoutineRepository

@Service
class RoutineService @Autowired constructor(
    override val repository: RoutineRepository,
    override val mapper: RoutineMapper,
    val exerciseBlockService: ExerciseBlockService
): RemoteTrainerService<Routine, RoutineEntity> {

    override fun create(modelObject: Routine): Routine {
        val savedExerciseBlocks: List<ExerciseBlock> =
            modelObject.exerciseBlocks.map {
                exerciseBlockService.create(it)
            }

        return super.create(modelObject.copy(exerciseBlocks = savedExerciseBlocks))
    }

    override fun update(modelObject: Routine): Routine {
        // TODO check out differences between saved and updated.
        // Removed && unnamed blocks should be removed!
        val updatedExerciseBlocks: List<ExerciseBlock> =
            modelObject.exerciseBlocks.map {
                if (it.id == null) {
                    exerciseBlockService.create(it)
                } else {
                    exerciseBlockService.update(it)
                }
            }

        return super.update(modelObject.copy(exerciseBlocks = updatedExerciseBlocks))
    }

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

    fun processRoutineWeek(
        startingDate: LocalDate,
        endingDate: LocalDate
    ): Iterable<RoutineDay> {
        val routinesByDate: Map<LocalDate, List<Routine>> =
            getRoutines(startingDate, endingDate)
                .groupBy { it.date }

        return (0..6).map { dayIndex ->
            val currentDate = startingDate.plusDays(dayIndex)

            RoutineDay(
                date = currentDate,
                routines = routinesByDate[currentDate] ?: listOf()
            )
        }
    }
}