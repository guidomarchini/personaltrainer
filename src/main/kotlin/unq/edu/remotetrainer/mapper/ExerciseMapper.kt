package unq.edu.remotetrainer.mapper

import org.springframework.stereotype.Component
import unq.edu.remotetrainer.model.Exercise
import unq.edu.remotetrainer.persistence.entity.ExerciseEntity

@Component
class ExerciseMapper {

    fun toEntity(exercise: Exercise): ExerciseEntity {
        return ExerciseEntity (
            id = exercise.id,
            name = exercise.name,
            description = exercise.description,
            link = exercise.link
        )
    }

    fun toModel(exerciseEntity: ExerciseEntity): Exercise {
        return Exercise(
            id = exerciseEntity.id,
            name = exerciseEntity.name,
            description = exerciseEntity.description,
            link = exerciseEntity.link
        )
    }
}