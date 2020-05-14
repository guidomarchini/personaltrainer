package unq.edu.remotetrainer.application.sevice

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import unq.edu.remotetrainer.mapper.ExerciseMapper
import unq.edu.remotetrainer.model.Exercise
import unq.edu.remotetrainer.persistence.entity.ExerciseEntity
import unq.edu.remotetrainer.persistence.repository.ExerciseRepository

@Service
class ExerciseService constructor(
    @Autowired override val repository: ExerciseRepository,
    @Autowired override val mapper: ExerciseMapper
): RemoteTrainerService<Exercise, ExerciseEntity> {

    fun getExerciseByName(name: String): Exercise? {
        return repository.getByName(name)
            ?.let { mapper.toModel(it) }
    }
}