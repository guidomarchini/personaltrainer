package unq.edu.remotetrainer.application.ui

import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.ui.set
import org.springframework.web.bind.annotation.GetMapping
import unq.edu.remotetrainer.application.sevice.ExerciseService
import unq.edu.remotetrainer.model.Exercise

@Controller
class ExercisesUI constructor(
    val exerciseService: ExerciseService
) {

    @GetMapping("/exercises")
    fun exercises(model: Model): String {
        val exercises: Collection<Exercise> =
            exerciseService.getAllExercises()
        model["exercises"] = exercises

        print("returning $exercises")

        return "exercises"
    }
}