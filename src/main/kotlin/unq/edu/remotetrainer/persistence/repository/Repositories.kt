package unq.edu.remotetrainer.persistence.repository

import org.springframework.data.repository.CrudRepository
import unq.edu.remotetrainer.persistence.entity.*

interface ExerciseBlockRepository : CrudRepository<ExerciseBlockEntity, Int>

interface ExerciseRepetitionRepository : CrudRepository<ExerciseRepetitionEntity, Int>

interface ExerciseRepository : CrudRepository<ExerciseEntity, Int>

interface RoutineRepository : CrudRepository<RoutineEntity, Int>

interface TrackingProfileRepository : CrudRepository<TrackingProfileEntity, Int>

interface TrackingRepository : CrudRepository<TrackingEntity, Int>