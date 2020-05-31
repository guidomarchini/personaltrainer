package unq.edu.remotetrainer.mapper

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import unq.edu.remotetrainer.model.Routine
import unq.edu.remotetrainer.persistence.entity.RoutineEntity

@Component
class RoutineMapper constructor(
    @Autowired val exerciseBlockMapper: ExerciseBlockMapper
): RemoteTrainerMapper<Routine, RoutineEntity> {

    override fun toEntity(model: Routine): RoutineEntity {
        return RoutineEntity(
            id = model.id,
            date = model.date,
            shortDescription = model.shortDescription,
            exerciseBlocks = model.exerciseBlocks.map { exerciseBlockMapper.toEntity(it) }.toMutableList(),
            notes = model.notes
        )
    }

    override fun toModel(entity: RoutineEntity): Routine {
        return Routine(
            id = entity.id,
            date = entity.date,
            shortDescription = entity.shortDescription,
            exerciseBlocks = entity.exerciseBlocks.map { exerciseBlockMapper.toModel(it) },
            notes = entity.notes
        )
    }
}