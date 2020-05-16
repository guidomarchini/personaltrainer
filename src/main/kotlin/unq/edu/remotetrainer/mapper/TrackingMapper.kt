package unq.edu.remotetrainer.mapper

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import unq.edu.remotetrainer.model.Tracking
import unq.edu.remotetrainer.persistence.entity.TrackingEntity

@Component
class TrackingMapper constructor(
    @Autowired val exerciseTrackingMapper: ExerciseTrackingMapper,
    @Autowired val exerciseMapper: ExerciseMapper
): RemoteTrainerMapper<Tracking, TrackingEntity> {

    override fun toEntity(model: Tracking): TrackingEntity {
        return TrackingEntity(
            id = model.id,
            exercise = exerciseMapper.toEntity(model.exercise),
            exerciseTrackings = model.exerciseTrackings.map { exerciseTrackingMapper.toEntity(it) }
        )
    }

    override fun toModel(entity: TrackingEntity) : Tracking {
        return Tracking(
            id = entity.id,
            exercise = exerciseMapper.toModel(entity.exercise),
            exerciseTrackings = entity.exerciseTrackings.map { exerciseTrackingMapper.toModel(it) }
        )
    }
}