package unq.edu.remotetrainer.persistence.dao

import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import unq.edu.remotetrainer.persistence.definitions.TrackingProfileTable

class TrackingProfileDao(id: EntityID<Int>): IntEntity(id) {
    companion object : IntEntityClass<TrackingProfileDao>(TrackingProfileTable)

    var name by TrackingProfileTable.name
}