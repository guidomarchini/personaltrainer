package unq.edu.remotetrainer.persistence.dao

import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import unq.edu.remotetrainer.persistence.definitions.RoutineTable

class RoutineDao(id: EntityID<Int>): IntEntity(id) {
    companion object : IntEntityClass<RoutineDao>(RoutineTable)

    var date    by RoutineTable.date
    var notes   by RoutineTable.notes
}