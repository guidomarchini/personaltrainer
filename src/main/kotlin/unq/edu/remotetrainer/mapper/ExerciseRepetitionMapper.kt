package unq.edu.remotetrainer.mapper

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import unq.edu.remotetrainer.model.ExerciseRepetition
import unq.edu.remotetrainer.persistence.entity.ExerciseRepetitionEntity

@Component
class ExerciseRepetitionMapper constructor(
    @Autowired val exerciseMapper: ExerciseMapper
) {

    fun toEntity(exerciseRepetition: ExerciseRepetition): ExerciseRepetitionEntity {
        return ExerciseRepetitionEntity(
            id = exerciseRepetition.id,
            exercise = exerciseMapper.toEntity(exerciseRepetition.exercise),
            quantity = exerciseRepetition.quantity
        )
    }

    fun toModel(exerciseRepetitionEntity: ExerciseRepetitionEntity): ExerciseRepetition {
        return ExerciseRepetition(
            id = exerciseRepetitionEntity.id,
            exercise = exerciseMapper.toModel(exerciseRepetitionEntity.exercise),
            quantity = exerciseRepetitionEntity.quantity
        )
    }

}