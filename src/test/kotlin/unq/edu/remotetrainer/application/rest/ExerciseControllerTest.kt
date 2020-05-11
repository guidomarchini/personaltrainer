package unq.edu.remotetrainer.application.rest

import io.restassured.RestAssured
import io.restassured.RestAssured.*
import io.restassured.http.ContentType
import org.assertj.core.api.Assertions.assertThat
import org.hamcrest.Matchers.*
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.boot.web.server.LocalServerPort
import org.springframework.http.HttpStatus
import unq.edu.remotetrainer.application.sevice.ExerciseService
import unq.edu.remotetrainer.model.Exercise


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ExerciseControllerTest(
    @Autowired val exerciseService: ExerciseService,
    @LocalServerPort val port: Int
) {

    var pullups = Exercise(
        name = "pullups",
        description = "they're hard!"
    )
    var pushups = Exercise(
        name = "pushups",
        description = "you can start with these ones!"
    )

    @BeforeAll
    fun beforeAll() {
        RestAssured.port = port
        pullups = exerciseService.createExercise(pullups)
        pushups = exerciseService.createExercise(pushups)
    }

    @Test
    fun `get exercises`(@Autowired restTemplate: TestRestTemplate) {
        // arrange

        // act
        get("/api/exercises")
        // assert
            .then()
            .statusCode(HttpStatus.OK.value())
            .body("", hasSize<Exercise>(2))
            .body("find { it.name == 'pullups' }", notNullValue())
            .body("find { it.name == 'pushups' }", notNullValue())
    }

    @Test
    fun `get exercise by id`(@Autowired restTemplate: TestRestTemplate) {
        // arrange

        // act
        get("/api/exercises/{id}", pullups.id)
            // assert
            .then()
            .statusCode(HttpStatus.OK.value())
            .body("name", equalTo(pullups.name))
            .body("description", equalTo(pullups.description))
    }

    @Test
    fun `create exercise`(@Autowired restTemplate: TestRestTemplate) {
        // arrange
        val newExerciseName = "new name"
        val newExerciseDescription = "new description"
        val newExerciseLink = "new link"
        val newExercise: Exercise = Exercise(
            name = newExerciseName,
            description = newExerciseDescription,
            link = newExerciseLink
        )

        // act
        val newExerciseId: Int = given()
            .body(newExercise)
            .contentType(ContentType.JSON)
            .post("/api/exercises")
            // assert
            .then()
            .statusCode(HttpStatus.CREATED.value())
            .body("name", equalTo(newExerciseName))
            .body("description", equalTo(newExerciseDescription))
            .body("link", equalTo(newExerciseLink))
            .extract()
            .path("id")

        // remove the exercise
        exerciseService.deleteExercise(newExerciseId)
    }

    @Test
    fun `update exercise`(@Autowired restTemplate: TestRestTemplate) {
        // arrange
        val originalExerciseName = "new name"
        val originalExerciseDescription = "new description"
        val originalExerciseLink = "new link"
        val originalExercise: Exercise = Exercise(
            name = originalExerciseName,
            description = originalExerciseDescription,
            link = originalExerciseLink
        )

        val exerciseId: Int = given()
            .body(originalExercise)
            .contentType(ContentType.JSON)
            .post("/api/exercises")
            .then()
            .statusCode(HttpStatus.CREATED.value())
            .extract()
            .path("id")

        val updatedExerciseName = "new name"
        val updatedExerciseDescription = "new description"
        val updatedExerciseLink = "new link"
        val updatedExercise: Exercise = Exercise(
            id = exerciseId,
            name = updatedExerciseName,
            description = updatedExerciseDescription,
            link = updatedExerciseLink
        )

        // act
        given()
            .body(updatedExercise)
            .contentType(ContentType.JSON)
            .put("/api/exercises")
            // assert
            .then()
            .statusCode(HttpStatus.OK.value())
            .body("name", equalTo(updatedExerciseName))
            .body("description", equalTo(updatedExerciseDescription))
            .body("link", equalTo(updatedExerciseLink))

        // remove the exercise
        exerciseService.deleteExercise(exerciseId)
    }

    @Test
    fun `delete exercise`(@Autowired restTemplate: TestRestTemplate) {
        // arrange
        val newExerciseName = "new name"
        val newExerciseDescription = "new description"
        val newExerciseLink = "new link"
        val newExercise: Exercise = Exercise(
            name = newExerciseName,
            description = newExerciseDescription,
            link = newExerciseLink
        )

        val newExerciseId: Int = given()
            .body(newExercise)
            .contentType(ContentType.JSON)
            .post("/api/exercises")
            .then()
            .statusCode(HttpStatus.CREATED.value())
            .extract()
            .path("id")

        delete("/api/exercises/{id}", newExerciseId)
            // assert
            .then()
            .statusCode(HttpStatus.NO_CONTENT.value())

        assertThat(exerciseService.getExerciseById(newExerciseId)).isNull()
    }
}
