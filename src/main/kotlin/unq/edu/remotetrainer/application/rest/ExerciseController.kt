package unq.edu.remotetrainer.application.rest

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*
import unq.edu.remotetrainer.application.rest.exceptions.ExerciseNotFoundException
import unq.edu.remotetrainer.application.sevice.ExerciseService
import unq.edu.remotetrainer.model.Exercise

@RestController
@RequestMapping("/api")
class ExerciseController constructor(
    private val exerciseService: ExerciseService
){

    @GetMapping("/exercises")
    fun exercises(): Collection<Exercise> {
        return exerciseService.getAllExercises()
    }

    @GetMapping("/exercises/{id}")
    fun getExerciseById(@PathVariable id: Int): Exercise {
        return exerciseService.getExerciseById(id) ?: throw ExerciseNotFoundException(id)
    }

    @PostMapping("/exercises")
    @ResponseStatus(HttpStatus.CREATED)
    fun createExercise(@RequestBody exercise: Exercise): Exercise {
        return exerciseService.createExercise(exercise)
    }

    @PutMapping("/exercises")
    fun update(@RequestBody exercise: Exercise): Exercise {
        checkNotNull(exercise.id)
        return exerciseService.updateExercise(exercise)
    }

    @DeleteMapping("/exercises/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun deleteExercise(@PathVariable id: Int): Unit {
        // TODO not used
        exerciseService.deleteExercise(id)
    }
}