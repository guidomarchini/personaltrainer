package unq.edu.remotetrainer.api

/**
 * Exercise progression can be tracked with this class.
 * It can be marked up as favourite to put it on top of the list.
 */
data class Tracking (
        val id: Int?,
        val exerciseTracking: Collection<ExerciseTracking>,
        val favourite: Boolean
)