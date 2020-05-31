package unq.edu.remotetrainer.persistence.repository

import org.joda.time.LocalDate
import org.springframework.data.repository.CrudRepository
import unq.edu.remotetrainer.persistence.entity.*

interface ExerciseRepository : CrudRepository<ExerciseEntity, Int> {
    fun getByName(name: String): ExerciseEntity?
}

interface ExerciseBlockRepository : CrudRepository<ExerciseBlockEntity, Int> {
    fun getAllByNameNotNull(): Iterable<ExerciseBlockEntity>
}

interface ExerciseTrackingRepository : CrudRepository<ExerciseTrackingEntity, Int>

interface RoutineRepository : CrudRepository<RoutineEntity, Int> {

    /**
     * Gets all routines by date between the passed parameters.
     * @param from the starting date to search (inclusive)
     * @param to the ending date to search (inclusive)
     * @return the routines between the given dates
     */
    fun getAllByDateBetween(
        from: LocalDate,
        to: LocalDate
    ): Iterable<RoutineEntity>
}

interface TrackingRepository : CrudRepository<TrackingEntity, Int>

interface TrackingProfileRepository : CrudRepository<TrackingProfileEntity, Int>