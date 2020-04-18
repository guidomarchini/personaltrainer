package unq.edu.remotetrainer.persistence.definitions

import org.jetbrains.exposed.dao.id.IntIdTable

object ExerciseTable : IntIdTable() {
    val name = varchar(name = "name", length = 32)
    val description = varchar(name = "definition", length = 1024)
    val link = varchar(name = "link", length = 128).nullable()
}