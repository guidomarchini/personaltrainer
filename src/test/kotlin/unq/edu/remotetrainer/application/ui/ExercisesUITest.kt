package unq.edu.remotetrainer.application.ui

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.http.HttpStatus
import unq.edu.remotetrainer.application.sevice.ExerciseService
import unq.edu.remotetrainer.model.Exercise

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ExercisesUITest(@Autowired val exerciseService: ExerciseService) {

    @BeforeAll
    fun beforeAll() {
        exerciseService.createExercise(Exercise(
            name = "pullups",
            description = "they're hard!"
        ))

        exerciseService.createExercise(Exercise(
            name = "pushups",
            description = "you can start with these ones!"
        ))
    }

    @Test
    fun `home page`(@Autowired restTemplate: TestRestTemplate) {
        val entity = restTemplate.getForEntity("/", String::class.java)
        assertThat(entity.statusCode).isEqualTo(HttpStatus.OK)
        assertThat(entity.body).contains("<h1>Bienvenido a Remote Trainer!</h1>")
    }

    @Test
    fun `exercises page`(@Autowired restTemplate: TestRestTemplate) {
        val entity = restTemplate.getForEntity("/exercises", String::class.java)
        assertThat(entity.statusCode).isEqualTo(HttpStatus.OK)
        assertThat(entity.body).contains(">pullups</a>")
        assertThat(entity.body).contains(">they&#39;re hard!</p>") // "&#39;" == '
        assertThat(entity.body).contains(">pushups</a>")
        assertThat(entity.body).contains(">you can start with these ones!</p>")
    }

}