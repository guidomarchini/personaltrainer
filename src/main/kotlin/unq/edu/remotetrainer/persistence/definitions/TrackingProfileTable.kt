package unq.edu.remotetrainer.persistence.definitions

import org.jetbrains.exposed.dao.id.IntIdTable

object TrackingProfileTable : IntIdTable() {
    val name = varchar(name = "name", length = 32)
}