package unq.edu.remotetrainer.model

import org.joda.time.DateTime

/**
 * A daily routine.
 * It consists of a series of exercises for a given date.
 * Additional notes can be added at this level.
 */
data class Routine (
    val id: Int?,
    val date: DateTime,
    val exerciseBlocks: List<ExerciseBlock>,
    val notes: String
)