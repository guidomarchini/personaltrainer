package unq.edu.remotetrainer.mapper

import org.springframework.stereotype.Component
import unq.edu.remotetrainer.model.TrackingProfile
import unq.edu.remotetrainer.persistence.entity.TrackingProfileEntity

@Component
class TrackingProfileMapper constructor(
    val trackingMapper: TrackingMapper
) {

    fun toEntity(trackingProfile: TrackingProfile): TrackingProfileEntity {
        return TrackingProfileEntity(
            id = trackingProfile.id,
            name = trackingProfile.name,
            trackings = trackingProfile.trackings.map { trackingMapper.toEntity(it) }
        )
    }

    fun toModel(trackingProfileEntity: TrackingProfileEntity): TrackingProfile {
        return TrackingProfile(
            id = trackingProfileEntity.id,
            name = trackingProfileEntity.name,
            trackings = trackingProfileEntity.trackings.map { trackingMapper.toModel(it) }
        )
    }
}