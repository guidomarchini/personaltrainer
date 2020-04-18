package unq.edu.remotetrainer.model

import unq.edu.remotetrainer.model.Exercise

/**
 * Exercise progression can be tracked with this class.
 * It can be marked up as favourite to put it on top of the list.
 */
data class Tracking (
        val id: Int?,
        val exercise: Exercise,
        val quantity: Int,
        val favourite: Boolean
)