package unq.edu.remotetrainer.persistence.dao

import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import unq.edu.remotetrainer.persistence.definitions.ExerciseBlockTable

class ExerciseBlockDao(id: EntityID<Int>): IntEntity(id) {
    companion object : IntEntityClass<ExerciseBlockDao>(ExerciseBlockTable)

    var notes   by ExerciseBlockTable.notes
    var routine by RoutineDao referencedOn ExerciseBlockTable.routine
}