package unq.edu.remotetrainer.persistence.dao

import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import unq.edu.remotetrainer.persistence.definitions.ExerciseRepetitionTable

class ExerciseRepetitionDao(id: EntityID<Int>): IntEntity(id) {
    companion object : IntEntityClass<ExerciseRepetitionDao>(ExerciseRepetitionTable)

    var exercise        by ExerciseDao referencedOn ExerciseRepetitionTable.exercise
    var quantity        by ExerciseRepetitionTable.quantity
    var exerciseBlock   by ExerciseBlockDao referencedOn ExerciseRepetitionTable.exerciseBlock
}