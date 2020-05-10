package unq.edu.remotetrainer.model

/**
 * Exercise progression can be tracked with this class.
 * It can be marked up as favourite to put it on top of the list.
 */
data class Tracking (
        val id: Int? = null,
        val exercise: Exercise,
        val exerciseTrackings: List<ExerciseTracking>,
        val favourite: Boolean
)