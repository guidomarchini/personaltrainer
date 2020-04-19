package unq.edu.remotetrainer.mapper

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import unq.edu.remotetrainer.model.ExerciseTracking
import unq.edu.remotetrainer.persistence.entity.ExerciseTrackingEntity

@Component
class ExerciseTrackingMapper constructor(
    @Autowired val exerciseMapper: ExerciseMapper
) {

    fun toEntity(exerciseTracking: ExerciseTracking): ExerciseTrackingEntity {
        return ExerciseTrackingEntity(
            id = exerciseTracking.id,
            exercise = exerciseMapper.toEntity(exerciseTracking.exercise),
            quantity = exerciseTracking.quantity,
            date = exerciseTracking.date
        )
    }

    fun toModel(exerciseTrackingEntity: ExerciseTrackingEntity): ExerciseTracking {
        return ExerciseTracking (
            id = exerciseTrackingEntity.id,
            exercise = exerciseMapper.toModel(exerciseTrackingEntity.exercise),
            quantity = exerciseTrackingEntity.quantity,
            date = exerciseTrackingEntity.date
        )
    }
}