package unq.edu.remotetrainer.application.ui

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.AfterAll
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.http.HttpStatus
import unq.edu.remotetrainer.application.sevice.ExerciseService
import unq.edu.remotetrainer.model.Exercise
import unq.edu.remotetrainer.persistence.repository.ExerciseRepository

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ExercisesUITest @Autowired constructor(
    val exerciseService: ExerciseService,
    val exerciseRepository: ExerciseRepository // used only in tear down method
) {

    @BeforeAll
    fun beforeAll() {
        exerciseService.create(Exercise(
            name = "pullups",
            description = "they're hard!"
        ))

        exerciseService.create(Exercise(
            name = "pushups",
            description = "you can start with these ones!"
        ))
    }

    @AfterAll
    fun tearDown() {
        exerciseRepository.deleteAll()
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