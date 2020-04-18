package unq.edu.remotetrainer.persistence.dao

import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import unq.edu.remotetrainer.persistence.definitions.ExerciseTable

class ExerciseDao(id: EntityID<Int>): IntEntity(id) {
    companion object : IntEntityClass<ExerciseDao>(ExerciseTable)

    var name        by ExerciseTable.name
    var description by ExerciseTable.description
    var link        by ExerciseTable.link
}