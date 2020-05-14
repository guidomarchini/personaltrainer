package unq.edu.remotetrainer.mapper

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import unq.edu.remotetrainer.model.ExerciseRepetition
import unq.edu.remotetrainer.persistence.entity.ExerciseRepetitionEntity

@Component
class ExerciseRepetitionMapper constructor(
    @Autowired val exerciseMapper: ExerciseMapper
): RemoteTrainerMapper<ExerciseRepetition, ExerciseRepetitionEntity> {

    override fun toEntity(model: ExerciseRepetition): ExerciseRepetitionEntity {
        return ExerciseRepetitionEntity(
            exercise = exerciseMapper.toEntity(model.exercise),
            quantity = model.quantity
        )
    }

    override fun toModel(entity: ExerciseRepetitionEntity): ExerciseRepetition {
        return ExerciseRepetition(
            exercise = exerciseMapper.toModel(entity.exercise),
            quantity = entity.quantity
        )
    }

}