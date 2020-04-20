package unq.edu.remotetrainer.persistence.repository

import org.springframework.data.repository.CrudRepository
import unq.edu.remotetrainer.persistence.entity.*
import java.util.*

interface ExerciseRepository : CrudRepository<ExerciseEntity, Int> {
    fun getByName(name: String): ExerciseEntity?
    fun getById(id: Int): ExerciseEntity?
    fun getExerciseEntitiesByNameIn(names: Collection<String>): Iterable<ExerciseEntity>
}

interface ExerciseBlockRepository : CrudRepository<ExerciseBlockEntity, Int>

interface ExerciseRepetitionRepository : CrudRepository<ExerciseRepetitionEntity, Int>

interface ExerciseTrackingRepository : CrudRepository<ExerciseTrackingEntity, Int>

interface RoutineRepository : CrudRepository<RoutineEntity, Int>

interface TrackingRepository : CrudRepository<TrackingEntity, Int>

interface TrackingProfileRepository : CrudRepository<TrackingProfileEntity, Int>