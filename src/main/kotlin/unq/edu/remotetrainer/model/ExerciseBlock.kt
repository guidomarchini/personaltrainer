package unq.edu.remotetrainer.model

/**
 * An {@link ExerciseBlock} is a sequence of exercises that can be repeated N times.
 */
data class ExerciseBlock (
    val id: Int?,

    /** The list of the exercises, with their repetitions */
    val exercises: List<ExerciseRepetition>,

    /** Notes of the trainer about this block */
    val notes: String
)