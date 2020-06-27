package unq.edu.remotetrainer.application.dto

import unq.edu.remotetrainer.model.Exercise
import unq.edu.remotetrainer.model.ExerciseBlock
import unq.edu.remotetrainer.model.Routine

data class ImportRequest (
    val exercises: List<Exercise> = emptyList(),
    val exerciseBlocks: List<ExerciseBlock> = emptyList(),
    val routines: List<Routine> = emptyList()
)