package unq.edu.remotetrainer.mapper

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import unq.edu.remotetrainer.model.Tracking
import unq.edu.remotetrainer.persistence.entity.TrackingEntity

@Component
class TrackingMapper constructor(
    @Autowired val exerciseTrackingMapper: ExerciseTrackingMapper
) {

    fun toEntity(tracking: Tracking): TrackingEntity {
        return TrackingEntity(
            id = tracking.id,
            exerciseTrackings = tracking.exerciseTrackings.map { exerciseTrackingMapper.toEntity(it) },
            favourite = tracking.favourite
        )
    }

    fun toModel(trackingEntity: TrackingEntity) : Tracking {
        return Tracking(
            id = trackingEntity.id,
            exerciseTrackings = trackingEntity.exerciseTrackings.map { exerciseTrackingMapper.toModel(it) },
            favourite = trackingEntity.favourite
        )
    }
}