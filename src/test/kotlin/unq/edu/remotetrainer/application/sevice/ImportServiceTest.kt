package unq.edu.remotetrainer.application.sevice

import com.nhaarman.mockitokotlin2.*
import org.joda.time.LocalDate
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Test
import unq.edu.remotetrainer.model.Exercise
import unq.edu.remotetrainer.model.ExerciseBlock
import unq.edu.remotetrainer.model.ExerciseRepetition
import unq.edu.remotetrainer.model.Routine

internal class ImportServiceTest {
    private val exerciseServiceMock: ExerciseService = mock()
    private val exerciseBlockServiceMock: ExerciseBlockService = mock()
    private val routineServiceMock: RoutineService = mock()
    private val importService: ImportService = ImportService(
        exerciseServiceMock,
        exerciseBlockServiceMock,
        routineServiceMock
    )

    @AfterEach
    fun after() {
        reset(
            exerciseServiceMock,
            exerciseBlockServiceMock,
            routineServiceMock
        )
    }

    @Test
    fun `import only exercises - exercises exists`() {
        // arrange
        val pushups: Exercise =
            Exercise(
                name = "pushups",
                description = "gain muscle!"
            )

        val exercises: List<Exercise> = listOf(pushups)
        val exerciseBlocks: List<ExerciseBlock> = emptyList()
        val routines: List<Routine> = emptyList()

        val exerciseNames: List<String> = listOf(pushups.name)

        // services getters
        whenever(exerciseServiceMock.getExerciseWithNameIn(exerciseNames))
            .thenReturn(listOf(pushups))
        whenever(exerciseBlockServiceMock.getExerciseBlocksWithNameIn(emptyList()))
            .thenReturn(emptyList())

        //services creates - no creation here

        // act
        importService.import(
            exercises,
            exerciseBlocks,
            routines
        )

        // assert
        verifyZeroInteractions(routineServiceMock)
        verify(exerciseBlockServiceMock).getExerciseBlocksWithNameIn(emptyList())
        verifyNoMoreInteractions(exerciseBlockServiceMock)
        verify(exerciseServiceMock).getExerciseWithNameIn(exerciseNames)
        verifyNoMoreInteractions(exerciseServiceMock)
    }

    @Test
    fun `import only exercises - pullups does not exist`() {
        // arrange
        val pushups: Exercise =
            Exercise(
                name = "pushups",
                description = "gain muscle!"
            )
        val pullups: Exercise =
            Exercise(
                name = "pullups",
                description = "they're hard!"
            )

        val exercises: List<Exercise> = listOf(pushups, pullups)
        val exerciseBlocks: List<ExerciseBlock> = emptyList()
        val routines: List<Routine> = emptyList()

        val exerciseNames: List<String> = listOf(pushups.name, pullups.name)

        // services getters
        whenever(exerciseServiceMock.getExerciseWithNameIn(exerciseNames))
            .thenReturn(listOf(pushups))
        whenever(exerciseBlockServiceMock.getExerciseBlocksWithNameIn(emptyList()))
            .thenReturn(emptyList())

        // services saves
        whenever(exerciseServiceMock.create(pullups)).thenReturn(pullups)

        // act
        importService.import(
            exercises,
            exerciseBlocks,
            routines
        )

        // assert
        verifyZeroInteractions(routineServiceMock)
        verify(exerciseBlockServiceMock).getExerciseBlocksWithNameIn(emptyList())
        verifyNoMoreInteractions(exerciseBlockServiceMock)

        verify(exerciseServiceMock).getExerciseWithNameIn(exerciseNames)
        verify(exerciseServiceMock).create(pullups)
        verifyNoMoreInteractions(exerciseServiceMock)
    }

    @Test
    fun `import only exercise blocks - existing exercise block for that name`() {
        // arrange
        val pushups: Exercise =
            Exercise(
                name = "pushups",
                description = "gain muscle!"
            )
        val pullups: Exercise =
            Exercise(
                name = "pullups",
                description = "they're hard!"
            )

        val warmUp: ExerciseBlock = ExerciseBlock(
            name = "warm up",
            notes = "always do this before you train",
            exercises = listOf(
                ExerciseRepetition(pushups, 10),
                ExerciseRepetition(pullups, 2)
            )
        )

        val exercises: List<Exercise> = emptyList()
        val exerciseBlocks: List<ExerciseBlock> = listOf(warmUp)
        val routines: List<Routine> = emptyList()

        val exerciseNames: List<String> = listOf(pushups.name, pullups.name)
        val exerciseBlockNames: List<String> = listOf(warmUp.name!!)

        // services getters
        whenever(exerciseServiceMock.getExerciseWithNameIn(exerciseNames))
            .thenReturn(listOf(pushups, pullups))
        whenever(exerciseBlockServiceMock.getExerciseBlocksWithNameIn(exerciseBlockNames))
            .thenReturn(listOf(warmUp))

        //services creates - no creation here

        // act
        importService.import(
            exercises,
            exerciseBlocks,
            routines
        )

        // assert
        verifyZeroInteractions(routineServiceMock)
        verify(exerciseBlockServiceMock).getExerciseBlocksWithNameIn(exerciseBlockNames)
        verifyNoMoreInteractions(exerciseBlockServiceMock)

        verify(exerciseServiceMock).getExerciseWithNameIn(exerciseNames)
        verifyNoMoreInteractions(exerciseServiceMock)
    }

    @Test
    fun `import only exercise blocks - creates exercise block and its unexistent exercises`() {
        // arrange
        val pushups: Exercise =
            Exercise(
                name = "pushups",
                description = "gain muscle!"
            )
        val pullups: Exercise =
            Exercise(
                name = "pullups",
                description = "they're hard!"
            )

        val warmUp: ExerciseBlock = ExerciseBlock(
            name = "warm up",
            notes = "always do this before you train",
            exercises = listOf(
                ExerciseRepetition(pushups, 10),
                ExerciseRepetition(pullups, 2)
            )
        )

        val exercises: List<Exercise> = emptyList()
        val exerciseBlocks: List<ExerciseBlock> = listOf(warmUp)
        val routines: List<Routine> = emptyList()

        val exerciseNames: List<String> = listOf(pushups.name, pullups.name)
        val exerciseBlockNames: List<String> = listOf(warmUp.name!!)

        // services getters
        whenever(exerciseServiceMock.getExerciseWithNameIn(exerciseNames))
            .thenReturn(listOf(pushups))
        whenever(exerciseBlockServiceMock.getExerciseBlocksWithNameIn(exerciseBlockNames))
            .thenReturn(emptyList())

        //services creates
        whenever(exerciseServiceMock.create(pullups)).thenReturn(pullups)

        // act
        importService.import(
            exercises,
            exerciseBlocks,
            routines
        )

        // assert
        verifyZeroInteractions(routineServiceMock)

        verify(exerciseBlockServiceMock).getExerciseBlocksWithNameIn(exerciseBlockNames)
        verify(exerciseBlockServiceMock).create(warmUp)
        verifyNoMoreInteractions(exerciseBlockServiceMock)

        verify(exerciseServiceMock).getExerciseWithNameIn(exerciseNames)
        verify(exerciseServiceMock).create(pullups)
        verifyNoMoreInteractions(exerciseServiceMock)
    }

    @Test
    fun `import only routines - creates the routine, with its exercise block and its unexistent exercises`() {
        // arrange
        val pushups: Exercise =
            Exercise(
                name = "pushups",
                description = "gain muscle!"
            )
        val pullups: Exercise =
            Exercise(
                name = "pullups",
                description = "they're hard!"
            )

        val warmUp: ExerciseBlock =
            ExerciseBlock(
                notes = "always do this before you train",
                exercises = listOf(
                    ExerciseRepetition(pushups, 10),
                    ExerciseRepetition(pullups, 2)
                )
            )

        val routine: Routine =
            Routine(
                shortDescription = "every day warm up",
                notes = "do this every day to get stronger",
                date = LocalDate.now(),
                exerciseBlocks = listOf(warmUp)
            )

        val exercises: List<Exercise> = emptyList()
        val exerciseBlocks: List<ExerciseBlock> = emptyList()
        val routines: List<Routine> = listOf(routine)

        val exerciseNames: List<String> = listOf(pushups.name, pullups.name)
        val exerciseBlockNames: List<String> = emptyList()

        // services getters
        whenever(exerciseServiceMock.getExerciseWithNameIn(exerciseNames))
            .thenReturn(listOf(pushups))
        whenever(exerciseBlockServiceMock.getExerciseBlocksWithNameIn(exerciseBlockNames))
            .thenReturn(emptyList())

        //services creates
        whenever(exerciseServiceMock.create(pullups)).thenReturn(pullups)
        whenever(exerciseBlockServiceMock.create(warmUp)).thenReturn(warmUp)

        // act
        importService.import(
            exercises,
            exerciseBlocks,
            routines
        )

        // assert
        verify(routineServiceMock).create(routine)
        verifyNoMoreInteractions(routineServiceMock)

        verify(exerciseBlockServiceMock).getExerciseBlocksWithNameIn(exerciseBlockNames)
        verify(exerciseBlockServiceMock).create(warmUp)
        verifyNoMoreInteractions(exerciseBlockServiceMock)

        verify(exerciseServiceMock).getExerciseWithNameIn(exerciseNames)
        verify(exerciseServiceMock).create(pullups)
        verifyNoMoreInteractions(exerciseServiceMock)
    }
}