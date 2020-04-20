package unq.edu.remotetrainer

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseBody
import org.springframework.web.bind.annotation.ResponseStatus
import unq.edu.remotetrainer.application.controller.exceptions.ExerciseNotFoundException

/**
 * Defines the exceptions return http status code
 */
@ControllerAdvice
class RemoteTrainerAdvice {
    @ResponseBody
    @ExceptionHandler
    @ResponseStatus(HttpStatus.NOT_FOUND)
    fun exerciseNotFoundHandler(exception: ExerciseNotFoundException): String {
        return exception.toString()
    }
}