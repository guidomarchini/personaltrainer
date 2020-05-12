package unq.edu.remotetrainer.mapper

import org.springframework.stereotype.Component
import unq.edu.remotetrainer.model.ExerciseTracking
import unq.edu.remotetrainer.persistence.entity.ExerciseTrackingEntity

@Component
class ExerciseTrackingMapper: RemoteTrainerMapper<ExerciseTracking, ExerciseTrackingEntity> {

    override fun toEntity(model: ExerciseTracking): ExerciseTrackingEntity {
        return ExerciseTrackingEntity(
            id = model.id,
            quantity = model.quantity,
            date = model.date
        )
    }

    override fun toModel(entity: ExerciseTrackingEntity): ExerciseTracking {
        return ExerciseTracking (
            id = entity.id,
            quantity = entity.quantity,
            date = entity.date
        )
    }
}