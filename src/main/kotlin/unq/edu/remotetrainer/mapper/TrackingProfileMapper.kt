package unq.edu.remotetrainer.mapper

import org.springframework.stereotype.Component
import unq.edu.remotetrainer.model.TrackingProfile
import unq.edu.remotetrainer.persistence.entity.TrackingProfileEntity

@Component
class TrackingProfileMapper constructor(
    val trackingMapper: TrackingMapper
): RemoteTrainerMapper<TrackingProfile, TrackingProfileEntity> {

    override fun toEntity(model: TrackingProfile): TrackingProfileEntity {
        return TrackingProfileEntity(
            id = model.id,
            name = model.name,
            trackings = model.trackings.map { trackingMapper.toEntity(it) }
        )
    }

    override fun toModel(entity: TrackingProfileEntity): TrackingProfile {
        return TrackingProfile(
            id = entity.id,
            name = entity.name,
            trackings = entity.trackings.map { trackingMapper.toModel(it) }
        )
    }
}