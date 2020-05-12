package unq.edu.remotetrainer.model

/**
 * An {@link ExerciseBlock} is a sequence of exercises that can be repeated N times.
 */
data class ExerciseBlock (
    val id: Int? = null,

    /**
     * Some ExerciseBlocks are to be repeated frequently (i.e. warm ups).
     * Those have a name to be easily referenced later on.
     */
    val name: String? = null,

    /** The list of the exercises, with their repetitions */
    val exercises: List<ExerciseRepetition>,

    /** Notes of the trainer about this block */
    val notes: String
)