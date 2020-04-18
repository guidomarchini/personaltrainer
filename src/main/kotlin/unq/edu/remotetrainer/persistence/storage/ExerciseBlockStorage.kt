package unq.edu.remotetrainer.persistence.storage

import unq.edu.remotetrainer.persistence.RemoteTrainerDatabase
import remotetrainer.storage.ExerciseRepetitionStorage
import unq.edu.remotetrainer.persistence.definitions.ExerciseBlockTable
import javax.inject.Inject

class ExerciseBlockStorage
@Inject constructor(
    private val db: RemoteTrainerDatabase,
    private val exerciseRepetitionStorage: ExerciseRepetitionStorage
) {
    private val table = ExerciseBlockTable

    // TODO
}