package unq.edu.remotetrainer.persistence.storage

import org.jetbrains.exposed.sql.deleteWhere
import unq.edu.remotetrainer.persistence.RemoteTrainerDatabase
import remotetrainer.dao.RoutineDao
import unq.edu.remotetrainer.persistence.definitions.ExerciseBlockTable
import remotetrainer.definitions.RoutineTable
import remotetrainer.model.Routine
import javax.inject.Inject

class RoutineStorage
@Inject constructor(
    private val db: RemoteTrainerDatabase,
    private val exerciseBlockStorage: ExerciseBlockStorage
) {
    val table = RoutineTable

    // CREATE
    fun create(routine: Routine): RoutineDao = db.transaction {
        RoutineDao.new {
            date = routine.date
            notes = routine.notes
        }
    }

    // READ
    fun get(id: Int): RoutineDao? = db.transaction {
        RoutineDao.findById(id)
    }

    fun getAll(): Iterable<RoutineDao> = db.transaction {
        RoutineDao.all()
    }

    //UPDATE
    fun update(routine: Routine): Unit = db.transaction {
        val routineId: Int = checkNotNull(routine.id)

        table.update ({ table.id eq routineId }) {
            it[notes] = routine.notes
        }
    }

    // DELETE
    fun delete(routine: Routine): Unit = db.transaction {
        val routineId: Int = checkNotNull(routine.id)

        ExerciseBlockTable.deleteWhere { ExerciseBlockTable.routine eq routineId }
        RoutineTable.deleteWhere { table.id eq routineId }
    }
}