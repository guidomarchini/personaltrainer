package unq.edu.remotetrainer.application.rest

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*
import unq.edu.remotetrainer.application.rest.exception.TrackingNotFoundException
import unq.edu.remotetrainer.application.sevice.ExerciseService
import unq.edu.remotetrainer.application.sevice.TrackingService
import unq.edu.remotetrainer.model.Tracking

@RestController
@RequestMapping("/api")
class TrackingController constructor(
    val trackingService: TrackingService,
    val exerciseService: ExerciseService
){
    @GetMapping("/trackings")
    fun trackings(): List<Tracking> {
        return trackingService.getAll()
    }

    @GetMapping("/trackings/{id}")
    fun trackingById(@PathVariable id: Int): Tracking {
        return trackingService.getById(id) ?: throw TrackingNotFoundException(id)
    }

    /**
     * Creates an empty tracking with no exercises
     */
    @PostMapping("/trackings")
    @ResponseStatus(HttpStatus.CREATED)
    fun createTrackingByExerciseId(@RequestBody exerciseId: Int): Tracking {
        return trackingService.createEmptyTrackingForExercise(exerciseId)
    }

    @PutMapping("/trackings/{id}")
    fun addTracking(
        @PathVariable id: Int,
        @RequestBody exerciseQuantity: Int
    ): Tracking {
        return trackingService.addExercise(id, exerciseQuantity)
    }

    @DeleteMapping("/trackings/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun deleteTracking(@RequestParam id: Int): Unit {
        trackingService.delete(id)
    }
}