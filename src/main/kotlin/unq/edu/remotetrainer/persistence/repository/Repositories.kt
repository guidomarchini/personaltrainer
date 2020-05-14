package unq.edu.remotetrainer.persistence.repository

import org.springframework.data.repository.CrudRepository
import unq.edu.remotetrainer.persistence.entity.*
import java.util.*

interface ExerciseRepository : CrudRepository<ExerciseEntity, Int> {
    fun getByName(name: String): ExerciseEntity?
}

interface ExerciseBlockRepository : CrudRepository<ExerciseBlockEntity, Int> {
    fun getAllByNameNotNull(): Iterable<ExerciseBlockEntity>
}

interface ExerciseTrackingRepository : CrudRepository<ExerciseTrackingEntity, Int>

interface RoutineRepository : CrudRepository<RoutineEntity, Int>

interface TrackingRepository : CrudRepository<TrackingEntity, Int>

interface TrackingProfileRepository : CrudRepository<TrackingProfileEntity, Int>