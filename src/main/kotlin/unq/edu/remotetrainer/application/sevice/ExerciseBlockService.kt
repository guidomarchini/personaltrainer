package unq.edu.remotetrainer.application.sevice

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import unq.edu.remotetrainer.mapper.ExerciseBlockMapper
import unq.edu.remotetrainer.model.ExerciseBlock
import unq.edu.remotetrainer.persistence.entity.ExerciseBlockEntity
import unq.edu.remotetrainer.persistence.repository.ExerciseBlockRepository

@Service
class ExerciseBlockService @Autowired constructor(
    override val repository: ExerciseBlockRepository,
    override val mapper: ExerciseBlockMapper
): RemoteTrainerService<ExerciseBlock, ExerciseBlockEntity> {

    fun getAllNamedBlocks(): List<ExerciseBlock> {
        return repository.getAllByNameNotNull().map {
            mapper.toModel(it)
        }
    }
}