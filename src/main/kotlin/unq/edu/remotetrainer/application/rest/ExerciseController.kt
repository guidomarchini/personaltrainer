package unq.edu.remotetrainer.application.rest

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

    @GetMapping("/exercises")
    fun exercises(): List<Exercise> {
        return exerciseService.getAllExercises()
    }

    @GetMapping("/exercises/{id}")
    fun getExerciseById(@PathVariable id: Int): Exercise {
        return exerciseService.getExerciseById(id) ?: throw ExerciseNotFoundException(id)
    }

    @PostMapping("/exercises")
    @ResponseStatus(HttpStatus.CREATED)
    fun createExercise(@RequestBody exercise: Exercise): Exercise {
        exerciseValidator.validateForCreate(exercise)
        return exerciseService.createExercise(exercise)
    }

    @PutMapping("/exercises")
    fun update(@RequestBody exercise: Exercise): Exercise {
        exerciseValidator.validateForUpdate(exercise)
        return exerciseService.updateExercise(exercise)
    }

    @DeleteMapping("/exercises/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun deleteExercise(@PathVariable id: Int): Unit {
        exerciseService.deleteExercise(id)
    }
}