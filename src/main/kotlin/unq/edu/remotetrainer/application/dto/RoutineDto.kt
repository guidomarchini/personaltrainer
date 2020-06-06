package unq.edu.remotetrainer.application.dto

import org.joda.time.LocalDate
import unq.edu.remotetrainer.model.ExerciseBlock
import unq.edu.remotetrainer.model.Routine

/**
 * Data transfer object representation of a routine
 */
data class RoutineDto (
    val id: Int? = null,
    val date: LocalDate = LocalDate(),
    val shortDescription: String = "",
    val notes: String = "",
    val exerciseBlocks: List<ExerciseBlock> = listOf()
) {
    constructor(routine: Routine): this(
        id = routine.id,
        date = routine.date,
        shortDescription = routine.shortDescription,
        notes = routine.notes,
        exerciseBlocks = routine.exerciseBlocks
    )
}