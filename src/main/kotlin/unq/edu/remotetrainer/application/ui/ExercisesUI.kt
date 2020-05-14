package unq.edu.remotetrainer.application.ui

import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.ui.set
import org.springframework.web.bind.annotation.GetMapping
import unq.edu.remotetrainer.application.sevice.ExerciseBlockService
import unq.edu.remotetrainer.application.sevice.ExerciseService
import unq.edu.remotetrainer.application.sevice.TrackingService
import unq.edu.remotetrainer.model.Exercise
import unq.edu.remotetrainer.model.Tracking

@Controller
class ExercisesUI constructor(
    val exerciseService: ExerciseService,
    val trackingService: TrackingService,
    val exerciseBlockService: ExerciseBlockService
) {

    @GetMapping("")
    fun home(model: Model): String {
        return "home"
    }

    @GetMapping("/exercises")
    fun exercises(model: Model): String {
        // TODO sorted by name
        model["exercises"] = exerciseService.getAll()

        return "exercises"
    }

    @GetMapping("/trackings")
    fun trackings(model: Model): String {

        val allTrackings: List<Tracking> = trackingService.getAll()

        val trackedExercises: Set<Exercise> =
            allTrackings.mapTo(HashSet(), { it.exercise })

        val exercisesNotBeingTracked: List<Exercise> =
            exerciseService.getAll().filter {
                !trackedExercises.contains(it)
        }

        // TODO sorted by favourite, then name
        // TODO add weight
        model["trackings"] = allTrackings
        model["exercises"] = exercisesNotBeingTracked

        return "trackings"
    }

    @GetMapping("/blocks")
    fun exerciseBlocks(model: Model): String {
        model["blocks"] = exerciseBlockService.getAllNamedBlocks()

        return "blocks"
    }
}