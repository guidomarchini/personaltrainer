package unq.edu.remotetrainer.application.rest

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*
import unq.edu.remotetrainer.application.rest.exception.ExerciseBlockNotFoundException
import unq.edu.remotetrainer.application.sevice.ExerciseBlockService
import unq.edu.remotetrainer.application.validator.ExerciseBlockValidator
import unq.edu.remotetrainer.model.ExerciseBlock

@RestController
@RequestMapping("/api")
class ExerciseBlockController constructor(
    private val exerciseBlockService: ExerciseBlockService,
    private val exerciseBlockValidator: ExerciseBlockValidator
){
    companion object {
        @JvmStatic
        private val logger: Logger = LoggerFactory.getLogger(javaClass.enclosingClass)
    }

    @GetMapping("/blocks")
    fun blocks(): List<ExerciseBlock> {
        logger.info("Retrieving all ExcerciseBlocks")
        return exerciseBlockService.getAll()
    }

    @GetMapping("/blocks/{id}")
    fun getExerciseBlockById(@PathVariable id: Int): ExerciseBlock {
        logger.info("Retrieving ExcerciseBlock with id: $id")
        return exerciseBlockService.getById(id) ?: throw ExerciseBlockNotFoundException(id)
    }

    @PostMapping("/blocks")
    @ResponseStatus(HttpStatus.CREATED)
    fun createExerciseBlock(@RequestBody exerciseBlock: ExerciseBlock): ExerciseBlock {
        logger.info("Creating ExerciseBlock: $exerciseBlock")
        exerciseBlockValidator.validateForCreate(exerciseBlock)
        return exerciseBlockService.create(exerciseBlock)
    }

    @PutMapping("/blocks")
    fun update(@RequestBody exerciseBlock: ExerciseBlock): ExerciseBlock {
        logger.info("Updating ExerciseBlock: $exerciseBlock")
        exerciseBlockValidator.validateForUpdate(exerciseBlock)
        return exerciseBlockService.update(exerciseBlock)
    }

    @DeleteMapping("/blocks/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun deleteExerciseBlock(@PathVariable id: Int): Unit {
        logger.info("Deleting ExerciseBlock with id: $id")
        exerciseBlockService.delete(id)
    }

}