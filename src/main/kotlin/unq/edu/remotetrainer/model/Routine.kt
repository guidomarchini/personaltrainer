package unq.edu.remotetrainer.model

import com.fasterxml.jackson.annotation.JsonFormat
import org.joda.time.LocalDate

/**
 * A daily routine.
 * It consists of a series of exercises for a given date.
 * Additional notes can be added at this level.
 */
data class Routine (
    val id: Int? = null,
    val date: LocalDate,
    val exerciseBlocks: List<ExerciseBlock>,
    val notes: String
)