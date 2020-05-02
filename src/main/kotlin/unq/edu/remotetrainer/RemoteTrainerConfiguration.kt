package unq.edu.remotetrainer

import org.springframework.boot.ApplicationRunner
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import unq.edu.remotetrainer.application.sevice.ExerciseService
import unq.edu.remotetrainer.model.Exercise

/**
 * Application configuration
 */
@Configuration
class RemoteTrainerConfiguration {

    @Bean
    fun dataInitializer(
        exerciseService: ExerciseService
    ) = ApplicationRunner {
//        exerciseService.createExercise(Exercise(
//            name = "pullups",
//            description = "they're hard!"
//        ))
//
//        exerciseService.createExercise(
//            Exercise(
//            name = "pushups",
//            description = "you can start with these ones!"
//        ))
    }
}