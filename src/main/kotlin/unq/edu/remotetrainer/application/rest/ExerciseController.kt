package unq.edu.remotetrainer.application.rest

import org.slf4j.Logger
import org.slf4j.LoggerFactory.getLogger
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*
import unq.edu.remotetrainer.application.rest.exception.ExerciseNotFoundException
import unq.edu.remotetrainer.application.sevice.ExerciseService
import unq.edu.remotetrainer.application.validator.ExerciseValidator
import unq.edu.remotetrainer.model.Exercise

@RestController
@RequestMapping("/api")
class ExerciseController constructor(
    private val exerciseService: ExerciseService,
    private val exerciseValidator: ExerciseValidator
){

    companion object {
        @JvmStatic
        private val logger: Logger = getLogger(javaClass.enclosingClass)
    }

    @GetMapping("/exercises")
    fun exercises(): List<Exercise> {
        logger.info("Retrieving all Exercises")
        return exerciseService.getAll()
    }

    @GetMapping("/exercises/{id}")
    fun getExerciseById(@PathVariable id: Int): Exercise {
        logger.info("Retrieving Exercise with id: $id")
        return exerciseService.getById(id) ?: throw ExerciseNotFoundException(id)
    }

    @PostMapping("/exercises")
    @ResponseStatus(HttpStatus.CREATED)
    fun createExercise(@RequestBody exercise: Exercise): Exercise {
        logger.info("Creating Exercise: $exercise")
        exerciseValidator.validateForCreate(exercise)
        return exerciseService.create(exercise)
    }

    @PutMapping("/exercises")
    fun update(@RequestBody exercise: Exercise): Exercise {
        logger.info("Updating Exercise: $exercise")
        exerciseValidator.validateForUpdate(exercise)
        return exerciseService.update(exercise)
    }

    @DeleteMapping("/exercises/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun deleteExercise(@PathVariable id: Int): Unit {
        logger.info("Deleting Exercise with id: $id")
        exerciseService.delete(id)
    }
}