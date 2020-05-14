package unq.edu.remotetrainer.application.validator

import org.springframework.stereotype.Service
import unq.edu.remotetrainer.application.sevice.ExerciseBlockService
import unq.edu.remotetrainer.model.ExerciseBlock

@Service
class ExerciseBlockValidator constructor(
    private val exerciseBlockService: ExerciseBlockService
) {
    private final val NAME_ALREADY_EXISTS_MESSAGE: String = "Another exercise block with that name exists."
    private final val NULL_ID_MESSAGE = "ExerciseBlock to update should exist in the database."

    fun validateForCreate(exerciseBlock: ExerciseBlock): Unit {
        val blockWithSameName: ExerciseBlock? =
            exerciseBlockService.getAllNamedBlocks().find {
                it.name == exerciseBlock.name
            }

        Validation.validate(
            condition = blockWithSameName == null,
            errorMessage = NAME_ALREADY_EXISTS_MESSAGE
        )
    }

    fun validateForUpdate(exerciseBlock: ExerciseBlock): Unit {
        // id should not be null
        val id: Int =
            Validation.validateNotNull(
                nullableObject = exerciseBlock.id,
                errorMessage = NULL_ID_MESSAGE
            )

        // name must be unique
        val anotherBlockWithSameName: ExerciseBlock? =
            exerciseBlockService.getAllNamedBlocks().find {
                it.name == exerciseBlock.name &&
                it.id != exerciseBlock.id
            }

        Validation.validateNull(
            nullableObject = anotherBlockWithSameName,
            errorMessage = NAME_ALREADY_EXISTS_MESSAGE
        )
    }
}