package unq.edu.remotetrainer.mapper

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import unq.edu.remotetrainer.model.ExerciseBlock
import unq.edu.remotetrainer.persistence.entity.ExerciseBlockEntity

@Component
class ExerciseBlockMapper constructor(
    @Autowired val exerciseRepetitionMapper: ExerciseRepetitionMapper
) {

    fun toEntity(exerciseBlock: ExerciseBlock): ExerciseBlockEntity {
        return ExerciseBlockEntity(
            id = exerciseBlock.id,
            notes = exerciseBlock.notes,
            exercises = exerciseBlock.exercises.map { exerciseRepetitionMapper.toEntity(it) }
        )
    }

    fun toModel(exerciseBlockEntity: ExerciseBlockEntity): ExerciseBlock {
        return ExerciseBlock(
            id = exerciseBlockEntity.id,
            notes = exerciseBlockEntity.notes,
            exercises = exerciseBlockEntity.exercises.map { exerciseRepetitionMapper.toModel(it) }
        )
    }
}