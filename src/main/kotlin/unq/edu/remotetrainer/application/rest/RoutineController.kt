package unq.edu.remotetrainer.application.rest

import org.joda.time.LocalDate
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*
import unq.edu.remotetrainer.application.sevice.RoutineService
import unq.edu.remotetrainer.application.validator.RoutineValidator
import unq.edu.remotetrainer.model.Routine

@RestController
@RequestMapping("/api")
class RoutineController constructor(
    private val routineService: RoutineService,
    private val routineValidator: RoutineValidator
){

    companion object {
        @JvmStatic
        private val logger: Logger = LoggerFactory.getLogger(javaClass.enclosingClass)
    }

    /**
     * Fetches routines.
     * If starting date AND ending date is passed as arguments,
     * then only the routines for that date range are returned (both dates included).
     */
    @GetMapping("/routines")
    fun routinesByWeek(
        @RequestParam startingDate: LocalDate?,
        @RequestParam endingDate: LocalDate?
    ): Iterable<Routine> {
        return if (startingDate != null && endingDate != null) {
            logger.info("Fetching routines from: [$startingDate] to: [$endingDate]")
            routineService.getRoutines(startingDate, endingDate)
        } else {
            logger.info("Fetching all routines")
            routineService.getAll()
        }
    }

    @GetMapping("/routines/{id}")
    fun getRoutineById(@PathVariable id: Int): Routine? {
        logger.info("Getting routine with id: $id")
        return routineService.getById(id)
    }

    @PostMapping("/routines")
    @ResponseStatus(HttpStatus.CREATED)
    fun createRoutine(@RequestBody routine: Routine): Routine {
        logger.info("Creating routine: $routine")
        return routineService.create(routine)
    }

    @PutMapping("/routines")
    fun updateRoutine(@RequestBody routine: Routine): Routine {
        logger.info("Updating routine: $routine")
        routineValidator.validateForUpdate(routine)
        return routineService.update(routine)
    }

    @DeleteMapping("/routines/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun deleteRoutine(@PathVariable id: Int): Unit {
        logger.info("deleting routine with id: $id")
        return routineService.delete(id)
    }
}