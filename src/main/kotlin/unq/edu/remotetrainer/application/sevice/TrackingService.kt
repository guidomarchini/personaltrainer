package unq.edu.remotetrainer.application.sevice

import org.joda.time.LocalDate
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import unq.edu.remotetrainer.application.rest.exception.ExerciseNotFoundException
import unq.edu.remotetrainer.application.rest.exception.TrackingNotFoundException
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
    @Autowired override val repository: TrackingRepository,
    @Autowired override val mapper: TrackingMapper,
    @Autowired val exerciseTrackingRepository: ExerciseTrackingRepository,
    @Autowired val exerciseTrackingMapper: ExerciseTrackingMapper,
    @Autowired val exerciseRepository: ExerciseRepository
): RemoteTrainerService<Tracking, TrackingEntity> {

    fun createEmptyTrackingForExercise(exerciseId: Int): Tracking {
        val exerciseEntity: ExerciseEntity =
            exerciseRepository.findByIdOrNull(exerciseId) ?: throw ExerciseNotFoundException(exerciseId)

        val newTracking: TrackingEntity = TrackingEntity(
            exercise = exerciseEntity,
            exerciseTrackings = listOf(),
            favourite = false
        )

        return mapper.toModel(
            repository.save(
                newTracking
            )
        )
    }

    fun addExercise(
        trackingId: Int,
        exerciseQuantity: Int
    ): Tracking {
        val tracking: TrackingEntity = repository.findByIdOrNull(trackingId)?: throw TrackingNotFoundException(trackingId)
        val exerciseTracking: ExerciseTracking = ExerciseTracking(
            quantity = exerciseQuantity,
            date = LocalDate.now()
        )
        val exerciseTrackingEntity: ExerciseTrackingEntity =
            exerciseTrackingRepository.save(exerciseTrackingMapper.toEntity(exerciseTracking))

        tracking.exerciseTrackings = tracking.exerciseTrackings.plus(exerciseTrackingEntity)

        return mapper.toModel(
            repository.save(tracking)
        )
    }

    fun toggleFavourite(id: Int): Unit {
        val trackingToUpdate: TrackingEntity =
            checkNotNull(repository.findByIdOrNull(id))

        trackingToUpdate.favourite = !trackingToUpdate.favourite

        repository.save(trackingToUpdate)
    }

    fun updateTracking(tracking: Tracking): Tracking {
        val id: Int = checkNotNull(tracking.id)
        val trackingToUpdate: TrackingEntity =
            checkNotNull(repository.findByIdOrNull(id))

        trackingToUpdate.exerciseTrackings =
            tracking.exerciseTrackings.map {
                exerciseTrackingMapper.toEntity(it)
            }

        repository.save(trackingToUpdate)

        return mapper.toModel(trackingToUpdate)
    }
}