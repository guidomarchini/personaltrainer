package unq.edu.remotetrainer.application.validator

import org.springframework.stereotype.Service
import unq.edu.remotetrainer.model.ExerciseBlock

@Service
class ImportValidator {

    fun validateExerciseBlocks(exerciseBlocks: List<ExerciseBlock>): Unit {
        Validation.validate(
            condition = exerciseBlocks.all { it.name != null },
            errorMessage = "All exercise blocks should have a name"
        )
    }
}
