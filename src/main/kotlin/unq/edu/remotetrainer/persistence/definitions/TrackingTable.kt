package unq.edu.remotetrainer.persistence.definitions

import org.jetbrains.exposed.dao.id.IntIdTable

object TrackingTable : IntIdTable() {
    val exercise = reference(name = "exercise_id", foreign = ExerciseTable)
    val quantity = integer(name = "quantity")
    val favourite = bool("favourite")
    val profile = reference(name = "tracking_profile_id", foreign = TrackingProfileTable)
}