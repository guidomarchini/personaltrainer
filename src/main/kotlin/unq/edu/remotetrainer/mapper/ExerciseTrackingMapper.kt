package unq.edu.remotetrainer.mapper

import org.springframework.stereotype.Component
import unq.edu.remotetrainer.model.ExerciseTracking
import unq.edu.remotetrainer.persistence.entity.ExerciseTrackingEntity

@Component
class ExerciseTrackingMapper constructor() {

    fun toEntity(exerciseTracking: ExerciseTracking): ExerciseTrackingEntity {
        return ExerciseTrackingEntity(
            id = exerciseTracking.id,
            quantity = exerciseTracking.quantity,
            date = exerciseTracking.date
        )
    }

    fun toModel(exerciseTrackingEntity: ExerciseTrackingEntity): ExerciseTracking {
        return ExerciseTracking (
            id = exerciseTrackingEntity.id,
            quantity = exerciseTrackingEntity.quantity,
            date = exerciseTrackingEntity.date
        )
    }
}