package unq.edu.remotetrainer.application.rest

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*
import unq.edu.remotetrainer.application.dto.ImportRequest
import unq.edu.remotetrainer.application.sevice.ImportService
import unq.edu.remotetrainer.application.validator.ImportValidator

@RestController
@RequestMapping("/api")
class ImportController constructor(
    val importService: ImportService,
    val importValidator: ImportValidator
) {

    companion object {
        @JvmStatic
        private val logger: Logger = LoggerFactory.getLogger(javaClass.enclosingClass)
    }

    @PostMapping("/import")
    @ResponseStatus(HttpStatus.CREATED)
    fun createRoutine(@RequestBody importRequest: ImportRequest): Unit {
        logger.info("Importing data: $importRequest")

        importValidator.validateExerciseBlocks(importRequest.exerciseBlocks)

        importService.import(
            importRequest.exercises,
            importRequest.exerciseBlocks,
            importRequest.routines
        )
    }
}