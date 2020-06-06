package unq.edu.remotetrainer.application.ui

import org.joda.time.LocalDate
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.ui.set
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestParam
import unq.edu.remotetrainer.application.dto.RoutineDto
import unq.edu.remotetrainer.application.sevice.ExerciseBlockService
import unq.edu.remotetrainer.application.sevice.ExerciseService
import unq.edu.remotetrainer.application.sevice.RoutineService
import unq.edu.remotetrainer.application.sevice.TrackingService
import unq.edu.remotetrainer.model.Exercise
import unq.edu.remotetrainer.model.Routine
import unq.edu.remotetrainer.model.Tracking

@Controller
class ExercisesUI constructor(
    val exerciseService: ExerciseService,
    val trackingService: TrackingService,
    val exerciseBlockService: ExerciseBlockService,
    val routineService: RoutineService
) {

    companion object {
        @JvmStatic
        private val logger: Logger = LoggerFactory.getLogger(javaClass.enclosingClass)
    }

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

        // TODO sorted by name
        // TODO add weight as static
        model["trackings"] = allTrackings
        model["exercises"] = exercisesNotBeingTracked

        return "trackings"
    }

    @GetMapping("/blocks")
    fun exerciseBlocks(model: Model): String {
        model["blocks"] = exerciseBlockService.getAllNamedBlocks()

        return "blocks"
    }

    @GetMapping("/routines")
    fun routines(
        model: Model,
        @RequestParam date: String?
    ): String {
        // can't receive a LocalDate :(
        val startingDay: LocalDate = if(date != null) LocalDate.parse(date) else weeksMonday()
        model["routineDays"] = routineService.processRoutineWeek(startingDay, startingDay.plusDays(6))

        return "routines"
    }

    @GetMapping("routines/create")
    fun createRoutine(
        model: Model,
        @RequestParam(name = "date") dateAsString: String
    ): String {
        // can't receive a LocalDate :(
        val parsedDate: LocalDate = LocalDate.parse(dateAsString)
        logger.info("creating a new routien for (default) date: $parsedDate")

        model["routine"] = RoutineDto(date = parsedDate)
        model["create"] = true

        return "upsert-routine"
    }

    @GetMapping("routines/update")
    fun updateRoutine(
        model: Model,
        @RequestParam routineId: Int
    ): String {
        logger.info("update routine with id: $routineId")

        val routineToUpdate: Routine = checkNotNull(routineService.getById(routineId))
        model["routine"] = RoutineDto(routineToUpdate)
        model["create"] = false

        return "upsert-routine"
    }

    @GetMapping("routines/copy")
    fun copyRoutine(
        model: Model,
        @RequestParam routineId: Int
    ): String {
        logger.info("update routine with id: $routineId")

        val routineToUpdate: Routine = checkNotNull(routineService.getById(routineId))
        model["routine"] = RoutineDto(routineToUpdate).copy(id = null)
        model["create"] = true

        return "upsert-routine"
    }


    /**
     * Returns the current monday of the week
     */
    private fun weeksMonday(): LocalDate {
        val date: LocalDate = LocalDate()
        return date.minusDays(date.dayOfWeek-1)
    }
}