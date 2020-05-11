package unq.edu.remotetrainer.application.validator

import unq.edu.remotetrainer.application.rest.exception.ValidationError

object Validation {
    /**
     * Validates that the condition is met.
     * If not, throws a {@link ValidationException}
     */
    fun validate(
        condition: Boolean,
        errorMessage: String
    ): Unit {
        if (!condition) {
            throw ValidationError(errorMessage)
        }
    }

    /**
     * Validates that the argument is not null.
     * If it is null, throws a {@link ValidationException}
     * If not, then returns the object, as a non null object
     */
    fun <T> validateNotNull(
        nullableObject: T?,
        errorMessage: String
    ): T {
        if (nullableObject == null) {
            throw ValidationError(errorMessage)
        } else {
            return nullableObject
        }
    }
}