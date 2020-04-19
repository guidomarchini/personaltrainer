package unq.edu.remotetrainer.mapper

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import unq.edu.remotetrainer.model.Routine
import unq.edu.remotetrainer.persistence.entity.RoutineEntity

@Component
class RoutineMapper constructor(
    @Autowired val exerciseBlockMapper: ExerciseBlockMapper
) {

    fun toEntity(routine: Routine): RoutineEntity {
        return RoutineEntity(
            id = routine.id,
            date = routine.date,
            exerciseBlocks = routine.exerciseBlocks.map { exerciseBlockMapper.toEntity(it) },
            notes = routine.notes
        )
    }

    fun toModel(routineEntity: RoutineEntity): Routine {
        return Routine(
            id = routineEntity.id,
            date = routineEntity.date,
            exerciseBlocks = routineEntity.exerciseBlocks.map { exerciseBlockMapper.toModel(it) },
            notes = routineEntity.notes
        )
    }
}