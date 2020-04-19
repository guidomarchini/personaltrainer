package unq.edu.remotetrainer.model

import org.joda.time.LocalDate

/**
 * An {@link ExerciseTracking} is the quantity of {@link Exercise}'s repetitions made in a given day
 */
data class ExerciseTracking (
    val id: Int?,
    val exercise: Exercise,
    val quantity: Int,
    val date: LocalDate
)