package unq.edu.remotetrainer.application.validator

import org.springframework.stereotype.Service
import unq.edu.remotetrainer.model.Routine

@Service
class RoutineValidator {
    private final val NULL_ID_MESSAGE = "Routine to update should exist in the database."

    fun validateForUpdate(routine: Routine): Unit {
        Validation.validate(
            condition = routine.id != null,
            errorMessage = NULL_ID_MESSAGE
        )
    }
}