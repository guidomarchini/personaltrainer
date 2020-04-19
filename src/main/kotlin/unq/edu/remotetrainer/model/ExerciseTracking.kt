package unq.edu.remotetrainer.api

import org.joda.time.LocalDate

/**
 * An {@link ExerciseTracking} is the quantity of {@link Exercise}'s repetitions made in a given day
 */
data class ExerciseTracking (
    val exercise: Exercise,
    val repetitions: Int,
    val date: LocalDate
)