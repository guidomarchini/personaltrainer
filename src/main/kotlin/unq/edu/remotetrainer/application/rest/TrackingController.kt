package unq.edu.remotetrainer.application.rest

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*
import unq.edu.remotetrainer.application.rest.exception.TrackingNotFoundException
import unq.edu.remotetrainer.application.sevice.TrackingService
import unq.edu.remotetrainer.model.Tracking

@RestController
@RequestMapping("/api")
class TrackingController constructor(
    val trackingService: TrackingService
){
    companion object {
        @JvmStatic
        private val logger: Logger = LoggerFactory.getLogger(javaClass.enclosingClass)
    }

    @GetMapping("/trackings")
    fun trackings(): List<Tracking> {
        logger.info("Retrieving all Trackings")
        return trackingService.getAll()
    }

    @GetMapping("/trackings/{id}")
    fun trackingById(@PathVariable id: Int): Tracking {
        logger.info("Retrieving Tracking with id: $id")
        return trackingService.getById(id) ?: throw TrackingNotFoundException(id)
    }

    /**
     * Creates an empty tracking with no exercises
     */
    @PostMapping("/trackings")
    @ResponseStatus(HttpStatus.CREATED)
    fun createTrackingByExerciseId(@RequestBody exerciseId: Int): Tracking {
        logger.info("Creating Tracking for Exercise with id: $exerciseId")
        return trackingService.createEmptyTrackingForExercise(exerciseId)
    }

    @PutMapping("/trackings/{id}")
    fun addTracking(
        @PathVariable id: Int,
        @RequestBody exerciseQuantity: Int
    ): Tracking {
        logger.info("Adding a new Tracking for tracking id: $id")
        return trackingService.addExercise(id, exerciseQuantity)
    }

    @DeleteMapping("/trackings/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun deleteTracking(@RequestParam id: Int): Unit {
        logger.info("Deleting Tracking with id: $id")
        trackingService.delete(id)
    }
}