package unq.edu.remotetrainer.mapper

import org.springframework.stereotype.Component
import unq.edu.remotetrainer.model.Exercise
import unq.edu.remotetrainer.persistence.entity.ExerciseEntity

@Component
class ExerciseMapper: RemoteTrainerMapper<Exercise, ExerciseEntity> {

    override fun toEntity(model: Exercise): ExerciseEntity {
        return ExerciseEntity (
            id = model.id,
            name = model.name,
            description = model.description,
            link = model.link
        )
    }

    override fun toModel(entity: ExerciseEntity): Exercise {
        return Exercise(
            id = entity.id,
            name = entity.name,
            description = entity.description,
            link = entity.link
        )
    }
}