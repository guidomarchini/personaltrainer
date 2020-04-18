package unq.edu.remotetrainer.persistence.dao

import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import remotetrainer.definitions.TrackingTable

class TrackingDao(id: EntityID<Int>): IntEntity(id) {
    companion object : IntEntityClass<TrackingDao>(TrackingTable)

    var exercise  by ExerciseDao referencedOn TrackingTable.exercise
    var quantity  by TrackingTable.quantity
    var favourite by TrackingTable.favourite
    var profile   by TrackingProfileDao referencedOn TrackingTable.profile
}