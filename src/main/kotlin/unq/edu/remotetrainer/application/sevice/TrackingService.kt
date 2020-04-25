package unq.edu.remotetrainer.application.sevice

import org.joda.time.LocalDate
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import unq.edu.remotetrainer.application.rest.exceptions.ExerciseNotFoundException
import unq.edu.remotetrainer.application.rest.exceptions.TrackingNotFoundException
import unq.edu.remotetrainer.mapper.ExerciseMapper
import unq.edu.remotetrainer.mapper.ExerciseTrackingMapper
import unq.edu.remotetrainer.mapper.TrackingMapper
import unq.edu.remotetrainer.model.ExerciseTracking
import unq.edu.remotetrainer.model.Tracking
import unq.edu.remotetrainer.persistence.entity.ExerciseEntity
import unq.edu.remotetrainer.persistence.entity.ExerciseTrackingEntity
import unq.edu.remotetrainer.persistence.entity.TrackingEntity
import unq.edu.remotetrainer.persistence.repository.ExerciseRepository
import unq.edu.remotetrainer.persistence.repository.ExerciseTrackingRepository
import unq.edu.remotetrainer.persistence.repository.TrackingRepository

@Service
class TrackingService constructor(
    @Autowired val trackingRepository: TrackingRepository,
    @Autowired val trackingMapper: TrackingMapper,
    @Autowired val exerciseTrackingRepository: ExerciseTrackingRepository,
    @Autowired val exerciseTrackingMapper: ExerciseTrackingMapper,
    @Autowired val exerciseRepository: ExerciseRepository,
    @Autowired val exerciseMapper: ExerciseMapper
) {
    fun createTracking(tracking: Tracking): Tracking {
        return trackingMapper.toModel(
            trackingRepository.save(
                trackingMapper.toEntity(tracking)
            )
        )
    }

    fun createEmptyTrackingForExercise(exerciseId: Int): Tracking {
        // TODO validate exercise not being tracked
        val exerciseEntity: ExerciseEntity =
            exerciseRepository.findByIdOrNull(exerciseId) ?: throw ExerciseNotFoundException(exerciseId)

        val newTracking: TrackingEntity = TrackingEntity(
            exercise = exerciseEntity,
            exerciseTrackings = listOf(),
            favourite = false
        )

        return trackingMapper.toModel(
            trackingRepository.save(
                newTracking
            )
        )
    }

    fun getAllTrackings(): Collection<Tracking> {
        return trackingRepository.findAll().map {
            trackingMapper.toModel(it)
        }
    }

    fun getTrackingById(id: Int): Tracking? {
        return trackingRepository.findByIdOrNull(id)
            ?.let { trackingMapper.toModel(it) }
    }

    fun addExercise(
        trackingId: Int,
        exerciseQuantity: Int
    ): Tracking {
        val tracking: TrackingEntity = trackingRepository.findByIdOrNull(trackingId)?: throw TrackingNotFoundException(trackingId)
        val exerciseTracking: ExerciseTracking = ExerciseTracking(
            quantity = exerciseQuantity,
            date = LocalDate.now()
        )
        val exerciseTrackingEntity: ExerciseTrackingEntity =
            exerciseTrackingRepository.save(exerciseTrackingMapper.toEntity(exerciseTracking))

        tracking.exerciseTrackings = tracking.exerciseTrackings.plus(exerciseTrackingEntity)

        return trackingMapper.toModel(
            trackingRepository.save(tracking)
        )
    }

    fun toggleFavourite(id: Int): Unit {
        val trackingToUpdate: TrackingEntity =
            checkNotNull(trackingRepository.findByIdOrNull(id))

        trackingToUpdate.favourite = !trackingToUpdate.favourite

        trackingRepository.save(trackingToUpdate)
    }

    fun updateTracking(tracking: Tracking): Tracking {
        val id: Int = checkNotNull(tracking.id)
        val trackingToUpdate: TrackingEntity =
            checkNotNull(trackingRepository.findByIdOrNull(id))

        trackingToUpdate.exerciseTrackings =
            tracking.exerciseTrackings.map {
                exerciseTrackingMapper.toEntity(it)
            }

        trackingRepository.save(trackingToUpdate)

        return trackingMapper.toModel(trackingToUpdate)
    }

    fun deleteTracking(id: Int): Unit {
        trackingRepository.deleteById(id)
    }
}