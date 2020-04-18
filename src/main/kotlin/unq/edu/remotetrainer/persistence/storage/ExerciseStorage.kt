package unq.edu.remotetrainer.persistence.storage

import unq.edu.remotetrainer.persistence.RemoteTrainerDatabase
import remotetrainer.dao.ExerciseDao
import remotetrainer.definitions.ExerciseTable
import remotetrainer.model.Exercise
import javax.inject.Inject

class ExerciseStorage
@Inject constructor(private val db: RemoteTrainerDatabase) {

    private val table = ExerciseTable;

    // CREATE
    fun create(exercise: Exercise): ExerciseDao = db.transaction {
        ExerciseDao.new {
            name = exercise.name
            description = exercise.description
            link = exercise.link
        }
    }

    // READ
    fun get(id: Int): ExerciseDao? = db.transaction {
        ExerciseDao.findById(id)
    }

    fun getAll(): Iterable<ExerciseDao> = db.transaction {
        ExerciseDao.all()
    }

    // UPDATE
    fun update(exercise: Exercise): Unit = db.transaction {
        val exerciseId: Int = checkNotNull(exercise.id)

        table.update ({ table.id eq exerciseId }) {
            it[name] = exercise.name
            it[description] = exercise.description
            it[link] = exercise.link
        }
    }

    // DELETE
    fun delete(exercise: Exercise): Unit = db.transaction {
        val exerciseId: Int = checkNotNull(exercise.id)

        table.deleteWhere { table.id eq exerciseId }
    }
}