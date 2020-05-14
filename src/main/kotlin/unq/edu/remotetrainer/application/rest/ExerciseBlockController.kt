package unq.edu.remotetrainer.application.rest

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

    @GetMapping("/blocks")
    fun blocks(): List<ExerciseBlock> {
        return exerciseBlockService.getAll()
    }

    @GetMapping("/blocks/{id}")
    fun getExerciseBlockById(@PathVariable id: Int): ExerciseBlock {
        return exerciseBlockService.getById(id) ?: throw ExerciseBlockNotFoundException(id)
    }

    @PostMapping("/blocks")
    @ResponseStatus(HttpStatus.CREATED)
    fun createExerciseBlock(@RequestBody exerciseBlock: ExerciseBlock): ExerciseBlock {
        exerciseBlockValidator.validateForCreate(exerciseBlock)
        return exerciseBlockService.create(exerciseBlock)
    }

    @PutMapping("/blocks")
    fun update(@RequestBody exerciseBlock: ExerciseBlock): ExerciseBlock {
        exerciseBlockValidator.validateForUpdate(exerciseBlock)
        return exerciseBlockService.update(exerciseBlock)
    }

    @DeleteMapping("/blocks/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun deleteExerciseBlock(@PathVariable id: Int): Unit {
        exerciseBlockService.delete(id)
    }

}