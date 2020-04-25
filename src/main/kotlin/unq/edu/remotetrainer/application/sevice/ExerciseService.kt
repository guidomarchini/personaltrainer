package unq.edu.remotetrainer.application.sevice

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import unq.edu.remotetrainer.mapper.ExerciseMapper
import unq.edu.remotetrainer.model.Exercise
import unq.edu.remotetrainer.persistence.entity.ExerciseEntity
import unq.edu.remotetrainer.persistence.repository.ExerciseRepository

@Service
class ExerciseService constructor(
    @Autowired val exerciseRepository: ExerciseRepository,
    @Autowired val exerciseMapper: ExerciseMapper
){
    fun createExercise(exercise: Exercise): Exercise {
        return exerciseMapper.toModel(
            exerciseRepository.save(
                exerciseMapper.toEntity(exercise)
            )
        )
    }

    fun getAllExercises() : Collection<Exercise> {
        return exerciseRepository.findAll().map {
            exerciseMapper.toModel(it)
        }
    }

    fun getExerciseById(id: Int): Exercise? {
        return exerciseRepository.getById(id)
            ?.let { exerciseMapper.toModel(it) }
    }

    fun updateExercise(exercise: Exercise): Exercise {
        // TODO find by name doesn't have different id

        val id: Int = checkNotNull(exercise.id)
        val exerciseToUpdate: ExerciseEntity =
            checkNotNull(exerciseRepository.findByIdOrNull(id))

        exerciseToUpdate.description = exercise.description
        exerciseToUpdate.name = exercise.name
        exerciseToUpdate.link = exercise.link

        return exerciseMapper.toModel(
            exerciseRepository.save(exerciseToUpdate)
        )
    }

    fun deleteExercise(id: Int): Unit {
        exerciseRepository.deleteById(id)
    }
}