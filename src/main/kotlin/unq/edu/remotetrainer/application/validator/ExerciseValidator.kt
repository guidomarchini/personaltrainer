package unq.edu.remotetrainer.application.validator

import org.springframework.stereotype.Service
import unq.edu.remotetrainer.application.sevice.ExerciseService
import unq.edu.remotetrainer.model.Exercise

@Service
class ExerciseValidator constructor(
    private val exerciseService: ExerciseService
){
    val NAME_ALREADY_EXISTS_MESSAGE: String = "Another exercise with that name exists."

    fun validateForCreate(exercise: Exercise): Unit {
        Validation.validate(
            exerciseService.getAllExercisesByname(exercise.name).isEmpty(),
            NAME_ALREADY_EXISTS_MESSAGE
        )
    }

    fun validateForUpdate(exercise: Exercise): Unit {
        // id should not be null
        val id: Int =
            Validation.validateNotNull(
                exercise.id,
                "Exercise should exist in the database."
            )

        // name must be unique
        val exercisesForName: List<Exercise> =
            exerciseService.getAllExercisesByname(exercise.name)

        Validation.validate(
            exercisesForName.all { it.id == id },
            NAME_ALREADY_EXISTS_MESSAGE
        )
    }

}