package unq.edu.remotetrainer.mapper

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import unq.edu.remotetrainer.model.ExerciseBlock
import unq.edu.remotetrainer.persistence.entity.ExerciseBlockEntity

@Component
class ExerciseBlockMapper constructor(
    @Autowired val exerciseRepetitionMapper: ExerciseRepetitionMapper
): RemoteTrainerMapper<ExerciseBlock, ExerciseBlockEntity> {

    override fun toEntity(model: ExerciseBlock): ExerciseBlockEntity {
        return ExerciseBlockEntity(
            id = model.id,
            name = model.name,
            notes = model.notes,
            exercises = model.exercises.map { exerciseRepetitionMapper.toEntity(it) }
        )
    }

    override fun toModel(entity: ExerciseBlockEntity): ExerciseBlock {
        return ExerciseBlock(
            id = entity.id,
            name = entity.name,
            notes = entity.notes,
            exercises = entity.exercises.map { exerciseRepetitionMapper.toModel(it) }
        )
    }
}