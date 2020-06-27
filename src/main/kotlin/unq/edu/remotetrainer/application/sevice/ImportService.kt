package unq.edu.remotetrainer.application.sevice

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import unq.edu.remotetrainer.model.Exercise
import unq.edu.remotetrainer.model.ExerciseBlock
import unq.edu.remotetrainer.model.ExerciseRepetition
import unq.edu.remotetrainer.model.Routine

@Service
class ImportService @Autowired constructor(
    val exerciseService: ExerciseService,
    val exerciseBlockService: ExerciseBlockService,
    val routineService: RoutineService
){

    companion object {
        @JvmStatic
        private val logger: Logger = LoggerFactory.getLogger(javaClass.enclosingClass)
    }

    /**
     * Imports the given exercises, exercise blocks and routines.
     * Some things to have in mind:
     * * Param exerciseBlocks should contain only named blocks.
     * * Exercises will be created at first, so we don't have troubles with duplicated names. Then...
     * * Both exerciseBlocks and Routines will have their exercises overridden for the existing ones
     */
    fun import(
        exercises: List<Exercise>,
        exerciseBlocks: List<ExerciseBlock>,
        routines: List<Routine>
    ): Unit {
        // exercises.map { it.name to it }.toMap()
        val totalExercises: Set<Exercise> =
            exercises
                .union(exerciseBlocks.flatMap { extractExercisesFromExerciseBlock(it) })
                .union(routines.flatMap { extractExercisesFromRoutine(it) })

        val exercisesByName: Map<String, Exercise> =
            saveExercises(totalExercises)

        saveExerciseBlocks(exerciseBlocks, exercisesByName)

        saveRoutines(routines, exercisesByName)
    }

    private fun extractExercisesFromExerciseBlock(exerciseBlock: ExerciseBlock): List<Exercise> {
        return exerciseBlock.exercises.map { it.exercise };
    }

    private fun extractExercisesFromRoutine(routine: Routine): List<Exercise> {
        return routine.exerciseBlocks.flatMap { extractExercisesFromExerciseBlock(it) }
    }

    /**
     * Saves the unexistent exercises.
     * Returns a map of exercises by their name.
     */
    private fun saveExercises(exercises: Set<Exercise>): Map<String, Exercise> {
        // first, gets all existing exercises for the exercises names
        val exerciseNames: List<String> =
            exercises.map{ it.name }

        val existingExercisesByName: Map<String, Exercise> =
            exerciseService.getExerciseWithNameIn(exerciseNames)
                .map { it.name to it }
                .toMap()

        // now, iterate the exercises again, making two things:
        // 1) save unexisting exercises
        // 2) making the resulting map
        return exercises.map {
            val existingExercise: Exercise? = existingExercisesByName[it.name]

            if (existingExercise != null) {
                logger.warn("Exercise with name ${it.name} already exists. Ignoring it.")
                it.name to existingExercise
            } else {
                logger.info("Saving exercise with name ${it.name}")
                val savedExercise: Exercise = exerciseService.create(it.copy(id = null)) // be sure to not have an id
                savedExercise.name to savedExercise
            }
        }.toMap()
    }

    /**
     * Saves the unexistent exercise blocks (by name), overriding their exercises
     */
    private fun saveExerciseBlocks(
        exerciseBlocks: List<ExerciseBlock>,
        exercisesByName: Map<String, Exercise>
    ): Unit {
        // first, fetch all exercise blocks with the same name as the ones we're trying to save
        val exerciseBlockNames: List<String> =
            exerciseBlocks.map { it.name!! } // exerciseBlocks should all have names at this point

        val existingExerciseBlockName: Set<String> =
            exerciseBlockService.getExerciseBlocksWithNameIn(exerciseBlockNames)
                .map { it.name!! } // we looked for named blocks, they all should have a name
                .toSet()

        // now iterates the exercise blocks, saving the unexisting ones, overriding their exercises.
        exerciseBlocks.forEach { exerciseBlock ->
            if (existingExerciseBlockName.contains(exerciseBlock.name)) {
                // exercise block with that name already exists
                logger.warn("ExerciseBlock with name ${exerciseBlock.name} already exists. Ignoring it.")
            } else {
                // unexisting exercise block, override its exercises and then save it
                logger.info("saving exercise block with name ${exerciseBlock.name}")
                saveExerciseBlock(exerciseBlock, exercisesByName)
            }
        }
    }

    /**
     * Saves an exerciseBlock. Exercises are overridden for the previously fetched exercises,
     * so all ExerciseRepetition should have its exercise inside exerciseByName.
     * This returns the saved {@link ExerciseBlock}.
     */
    private fun saveExerciseBlock(
        exerciseBlock: ExerciseBlock,
        exercisesByName: Map<String, Exercise>
    ): ExerciseBlock {
        val overriddenExercises: List<ExerciseRepetition> =
            exerciseBlock.exercises.map {
                val overriddenExercise: Exercise =
                    checkNotNull(exercisesByName[it.exercise.name])

                it.copy(exercise = overriddenExercise)
            }

        return exerciseBlockService.create(exerciseBlock.copy(
            id = null,
            exercises = overriddenExercises
        ))
    }

    private fun saveRoutines(
        routines: List<Routine>,
        exercisesByName: Map<String, Exercise>
    ): Unit {
        routines.forEach { routine ->
            val exerciseBlocks: List<ExerciseBlock> =
                routine.exerciseBlocks.map {
                    saveExerciseBlock(it, exercisesByName)
                }

            routineService.create(routine.copy(
                id = null,
                exerciseBlocks = exerciseBlocks
            ))
        }
    }
}