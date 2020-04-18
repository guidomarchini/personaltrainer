package unq.edu.remotetrainer.model

import unq.edu.remotetrainer.model.Exercise

data class ExerciseRepetition (
        val id: Int?,
        val exercise: Exercise,
        val quantity: Int
)