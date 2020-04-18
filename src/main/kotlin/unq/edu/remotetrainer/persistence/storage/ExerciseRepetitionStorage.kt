package unq.edu.remotetrainer.persistence.storage

import unq.edu.remotetrainer.persistence.RemoteTrainerDatabase
import unq.edu.remotetrainer.persistence.dao.ExerciseBlockDao
import remotetrainer.dao.ExerciseDao
import remotetrainer.dao.ExerciseRepetitionDao
import remotetrainer.definitions.ExerciseRepetitionTable
import remotetrainer.model.ExerciseRepetition
import javax.inject.Inject

class ExerciseRepetitionStorage
@Inject constructor(
    private val db: RemoteTrainerDatabase,
    private val exerciseStorage: ExerciseStorage
) {
    private val table = ExerciseRepetitionTable

    // CREATE
    fun create(
        exerciseRepetition: ExerciseRepetition,
        exerciseDao: ExerciseDao,
        exerciseBlockDao: ExerciseBlockDao
    ): ExerciseRepetitionDao = db.transaction {
        ExerciseRepetitionDao.new {
            exercise = exerciseDao
            quantity = exerciseRepetition.quantity
            exerciseBlock = exerciseBlockDao
        }
    }

    // READ
    fun get(id: Int): ExerciseRepetitionDao? = db.transaction {
        ExerciseRepetitionDao.findById(id)
    }

    fun getAll(): Iterable<ExerciseRepetitionDao> = db.transaction {
        ExerciseRepetitionDao.all()
    }

    // UPDATE
    fun update(exerciseRepetition: ExerciseRepetition): Unit {
        val exerciseRepetitionId: Int = checkNotNull(exerciseRepetition.id)

        val exerciseId: Int = checkNotNull(exerciseRepetition.exercise.id)
        val exerciseDao: ExerciseDao = checkNotNull(exerciseStorage.get(exerciseId))

        db.transaction {
            table.update ({ table.id eq exerciseRepetitionId }) {
                it[exercise] = exerciseDao.id
                it[quantity] = exerciseRepetition.quantity
            }
        }
    }

    // DELETE
    fun delete(exerciseRepetition: ExerciseRepetition): Unit = db.transaction {
        val exerciseRepetitionId: Int = checkNotNull(exerciseRepetition.id)

        table.deleteWhere { table.id eq exerciseRepetitionId }
    }
}