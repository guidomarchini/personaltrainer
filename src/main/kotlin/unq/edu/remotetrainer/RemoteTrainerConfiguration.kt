package unq.edu.remotetrainer

import org.joda.time.LocalDate
import org.springframework.boot.ApplicationRunner
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import unq.edu.remotetrainer.application.sevice.ExerciseBlockService
import unq.edu.remotetrainer.application.sevice.ExerciseService
import unq.edu.remotetrainer.application.sevice.RoutineService
import unq.edu.remotetrainer.model.Exercise
import unq.edu.remotetrainer.model.ExerciseBlock
import unq.edu.remotetrainer.model.ExerciseRepetition
import unq.edu.remotetrainer.model.Routine

/**
 * Application configuration
 */
@Configuration
class RemoteTrainerConfiguration {

    @Bean
    fun dataInitializer(
        exerciseService: ExerciseService,
        exerciseBlockService: ExerciseBlockService,
        routineService: RoutineService
    ) = ApplicationRunner {
        /* Create exercises */
//        val pullups: Exercise =
//            exerciseService.create(Exercise(
//                name = "Dominadas",
//                description = "Son dificiles!"
//            ))
//
//        val pushups =
//            exerciseService.create(Exercise(
//                name = "Flexiones de brazos",
//                description = "Podes empezar con estas. Trabaja ese pectoral"
//            ))
//
//        /* Create exercise blocks */
//        val exerciseBlock =
//            exerciseBlockService.create(ExerciseBlock(
//                name = "Bloque de ejercicios ejemplo",
//                notes = "Hacer los ejercicios por minuto, durante 10 minutos",
//                exercises = mutableListOf(
//                    ExerciseRepetition(
//                        exercise = pushups,
//                        quantity = 10
//                    ),
//                    ExerciseRepetition(
//                        exercise = pullups,
//                        quantity = 5
//                    )
//                )
//            ))
//
//        val routine: Routine =
//            routineService.create(Routine(
//                date = LocalDate.now(),
//                shortDescription = "Principiantes",
//                notes = "Cinco minutos de pausa entre cada serie",
//                exerciseBlocks = mutableListOf(ExerciseBlock(
//                    notes = "Hacer durante un minuto, por 5 minutos",
//                    exercises = mutableListOf(ExerciseRepetition(
//                        exercise = pushups,
//                        quantity = 10
//                    ))
//                ))
//            ))
    }
}