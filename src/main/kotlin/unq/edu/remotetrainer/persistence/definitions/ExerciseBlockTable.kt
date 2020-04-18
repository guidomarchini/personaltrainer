package unq.edu.remotetrainer.persistence.definitions

import org.jetbrains.exposed.dao.id.IntIdTable

object ExerciseBlockTable : IntIdTable() {
    val notes = varchar(name = "notes", length = 1024)
    val routine = reference(name = "routine", foreign = RoutineTable)
}