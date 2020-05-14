package unq.edu.remotetrainer.application.rest

import io.restassured.RestAssured
import io.restassured.RestAssured.*
import io.restassured.http.ContentType
import org.assertj.core.api.Assertions.assertThat
import org.hamcrest.Matchers.equalTo
import org.hamcrest.Matchers.notNullValue
import org.junit.jupiter.api.AfterAll
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.web.server.LocalServerPort
import org.springframework.http.HttpStatus
import unq.edu.remotetrainer.application.sevice.ExerciseService
import unq.edu.remotetrainer.model.Exercise


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
internal class ExerciseControllerTest (
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
        pullups = exerciseService.create(pullups)
        pushups = exerciseService.create(pushups)
    }

    @AfterAll
    fun afterAll() {
        exerciseService.delete(pullups.id!!)
        exerciseService.delete(pushups.id!!)
    }

    @Test
    fun `get exercises`() {
        // arrange

        // act
        get("/api/exercises")
        // assert
            .then()
            .statusCode(HttpStatus.OK.value())
            .body("size()", equalTo(2))
            .body("find { it.name == '${pullups.name}' }", notNullValue())
            .body("find { it.name == '${pushups.name}' }", notNullValue())
    }

    @Test
    fun `get exercise by id`() {
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
    fun `create exercise`() {
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
        exerciseService.delete(newExerciseId)
    }

    @Test
    fun `create exercise with same name as existing one`() {
        // arrange
        val exerciseName = "some name"

        val existingExercise: Exercise =
            exerciseService.create(
                Exercise(
                name = exerciseName,
                description = "some description"
            ))

        val newExerciseDescription = "new description"
        val newExerciseLink = "new link"

        val newExercise: Exercise = Exercise(
            name = exerciseName,
            description = newExerciseDescription,
            link = newExerciseLink
        )

        // act
        given()
            .body(newExercise)
            .contentType(ContentType.JSON)
            .post("/api/exercises")
            // assert
            .then()
            .statusCode(HttpStatus.BAD_REQUEST.value())

        // remove the exercise
        exerciseService.delete(existingExercise.id!!)
    }

    @Test
    fun `update exercise`() {
        // arrange
        val originalExerciseName = "new name"
        val originalExerciseDescription = "new description"
        val originalExerciseLink = "new link"
        val existingExercise: Exercise =
            exerciseService.create(
                Exercise(
                    name = originalExerciseName,
                    description = originalExerciseDescription,
                    link = originalExerciseLink
                ))

        val updatedExerciseName = "new name"
        val updatedExerciseDescription = "new description"
        val updatedExerciseLink = "new link"
        val updatedExercise: Exercise = Exercise(
            id = existingExercise.id,
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
        exerciseService.delete(existingExercise.id!!)
    }

    @Test
    fun `update exercise with the same name as existing one`() {
        // arrange
        val originalExerciseName = "new name"
        val originalExerciseDescription = "new description"
        val originalExerciseLink = "new link"
        val exerciseToUpdate: Exercise =
            exerciseService.create(
                Exercise(
                    name = originalExerciseName,
                    description = originalExerciseDescription,
                    link = originalExerciseLink
                ))

        val exerciseName = "some name"

        val existingExercise: Exercise =
            exerciseService.create(
                Exercise(
                    name = exerciseName,
                    description = "some description"
                ))

        val updatedExerciseDescription = "new description"
        val updatedExerciseLink = "new link"
        val updatedExercise: Exercise = Exercise(
            id = exerciseToUpdate.id,
            name = exerciseName,
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
            .statusCode(HttpStatus.BAD_REQUEST.value())

        // remove the exercise
        exerciseService.delete(exerciseToUpdate.id!!)
        exerciseService.delete(existingExercise.id!!)
    }

    @Test
    fun `delete exercise`() {
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

        assertThat(exerciseService.getById(newExerciseId)).isNull()
    }
}
