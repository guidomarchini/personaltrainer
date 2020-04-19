package unq.edu.remotetrainer.api

import org.joda.time.LocalDate

/**
 * A daily routine.
 * It consists of a series of exercises for a given date.
 * Additional notes can be added at this level.
 */
data class Routine (
    val id: Int?,
    val date: LocalDate,
    val exerciseBlocks: List<ExerciseBlock>,
    val notes: String
)