package unq.edu.remotetrainer.persistence.definitions

import org.jetbrains.exposed.dao.id.IntIdTable
import org.jetbrains.exposed.sql.jodatime.datetime

object RoutineTable : IntIdTable() {
    val date = datetime(name = "date")
    val notes = varchar(name = "notes", length = 2048)
}