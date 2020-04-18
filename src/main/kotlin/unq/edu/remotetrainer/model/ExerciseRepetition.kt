package unq.edu.remotetrainer.model

/**
 * Defines a quantity of a given exercise has to be done
 */
data class ExerciseRepetition (
        val id: Int?,
        val exercise: Exercise,
        val quantity: Int
)