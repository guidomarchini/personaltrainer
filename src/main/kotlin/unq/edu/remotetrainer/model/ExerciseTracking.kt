package unq.edu.remotetrainer.model

import org.joda.time.LocalDate

/**
 * An {@link ExerciseTracking} is the quantity of an {@link Exercise}'s repetitions made in a given day
 */
data class ExerciseTracking (
    val id: Int? = null,
    val quantity: Int,
    val date: LocalDate
)