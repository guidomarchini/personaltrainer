package unq.edu.remotetrainer.application.validator

import org.springframework.stereotype.Service
import unq.edu.remotetrainer.application.sevice.ExerciseService
import unq.edu.remotetrainer.model.Exercise

@Service
class ExerciseValidator constructor(
    private val exerciseService: ExerciseService
){
    private final val NAME_ALREADY_EXISTS_MESSAGE: String = "Another exercise with that name exists."
    private final val NULL_ID_MESSAGE = "Exercise to update should exist in the database."

    fun validateForCreate(exercise: Exercise): Unit {
        Validation.validateNull(
            nullableObject = exerciseService.getExerciseByName(exercise.name),
            errorMessage = NAME_ALREADY_EXISTS_MESSAGE
        )
    }

    fun validateForUpdate(exercise: Exercise): Unit {
        // id should not be null
        val id: Int =
            Validation.validateNotNull(
                nullableObject = exercise.id,
                errorMessage = NULL_ID_MESSAGE
            )

        // name must be unique
        val exercisesWithName: Exercise? =
            exerciseService.getExerciseByName(exercise.name)

        Validation.validate(
            condition = exercisesWithName == null || exercisesWithName.id == exercise.id,
            errorMessage = NAME_ALREADY_EXISTS_MESSAGE
        )
    }

}