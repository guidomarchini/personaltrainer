package unq.edu.remotetrainer.persistence.definitions

import org.jetbrains.exposed.dao.id.IntIdTable


object ExerciseRepetitionTable : IntIdTable() {
    val exercise = reference(name = "exercise", foreign = ExerciseTable)
    val quantity = integer(name = "quantity")
    val exerciseBlock = reference(name = "exercise_block_id", foreign = ExerciseBlockTable)
}