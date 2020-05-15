package unq.edu.remotetrainer.application.rest

import io.restassured.RestAssured
import io.restassured.http.ContentType
import org.assertj.core.api.Assertions.assertThat
import org.hamcrest.Matchers
import org.junit.jupiter.api.AfterAll
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.web.server.LocalServerPort
import org.springframework.http.HttpStatus
import unq.edu.remotetrainer.application.sevice.ExerciseBlockService
import unq.edu.remotetrainer.application.sevice.ExerciseService
import unq.edu.remotetrainer.model.Exercise
import unq.edu.remotetrainer.model.ExerciseBlock
import unq.edu.remotetrainer.model.ExerciseRepetition

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
internal class ExerciseBlockControllerTest (
    @Autowired val exerciseBlockService: ExerciseBlockService,
    @Autowired val exerciseService: ExerciseService,
    @LocalServerPort val port: Int
) {

    val exerciseBlock: ExerciseBlock = exerciseBlockService.create(
        ExerciseBlock(
            name = "example block",
            exercises = listOf(),
            notes = "example block"
        ))

    @BeforeAll
    fun beforeAll() {
        RestAssured.port = port
    }

    @AfterAll
    fun afterAll() {
        exerciseBlockService.delete(exerciseBlock.id!!)
    }

    @Test
    fun `get exercise blocks`() {
        // arrange

        // act
        RestAssured.get("/api/blocks")
            // assert
            .then()
            .statusCode(HttpStatus.OK.value())
            .body("size()", Matchers.equalTo(1))
            .body("find { it.name == '${exerciseBlock.name}' }", Matchers.notNullValue())
    }

    @Test
    fun `get exercise block by id`() {
        // arrange

        // act
        RestAssured.get("/api/blocks/{id}", exerciseBlock.id)
            // assert
            .then()
            .statusCode(HttpStatus.OK.value())
            .body("name", Matchers.equalTo(exerciseBlock.name))
    }

    @Test
    fun `create exercise block`() {
        // arrange
        val blockName: String = "block to create"
        val blockNotes: String = "block notes to create"
        val newExerciseBlock: ExerciseBlock = ExerciseBlock(
            name = blockName,
            exercises = listOf(),
            notes = blockNotes
        )

        // act
        val newExerciseBlockId: Int = RestAssured.given()
            .body(newExerciseBlock)
            .contentType(ContentType.JSON)
            .post("/api/blocks")
            // assert
            .then()
            .statusCode(HttpStatus.CREATED.value())
            .body("name", Matchers.equalTo(blockName))
            .body("notes", Matchers.equalTo(blockNotes))
            .body("exercises.size()", Matchers.equalTo(0))
            .extract()
            .path("id")

        // remove the exercise
        exerciseBlockService.delete(newExerciseBlockId)
    }

    @Test
    fun `it creates an exercise block with exercise repetitions`() {
        // arrange
        val exercise: Exercise = exerciseService.create(
            Exercise(
                name = "some exercise",
                description = "some exercise!"
            )
        )

        val exerciseRepetition: ExerciseRepetition = ExerciseRepetition(
            exercise = exercise,
            quantity = 1
        )

        val blockName: String = "block to create"
        val blockNotes: String = "block notes to create"
        val newExerciseBlock: ExerciseBlock = ExerciseBlock(
            name = blockName,
            exercises = listOf(exerciseRepetition),
            notes = blockNotes
        )

        // act
        val newExerciseBlockId: Int = RestAssured.given()
            .body(newExerciseBlock)
            .contentType(ContentType.JSON)
            .post("/api/blocks")
            // assert
            .then()
            .statusCode(HttpStatus.CREATED.value())
            .body("name", Matchers.equalTo(blockName))
            .body("notes", Matchers.equalTo(blockNotes))
            .body("exercises.size()", Matchers.equalTo(1))
            .body("exercises[0].quantity", Matchers.equalTo(exerciseRepetition.quantity))
            .body("exercises[0].exercise.id", Matchers.equalTo(exercise.id))
            .extract()
            .path("id")

        // remove the exercise
        exerciseBlockService.delete(newExerciseBlockId)
        exerciseService.delete(exercise.id!!)
    }

    @Test
    fun `it returns a bad request when creating exercise block with same name as previous one`() {
        // arrange
        val newExerciseBlock: ExerciseBlock = ExerciseBlock(
            name = exerciseBlock.name,
            exercises = listOf(),
            notes = "block notes to create"
        )

        // act
        RestAssured.given()
            .body(newExerciseBlock)
            .contentType(ContentType.JSON)
            .post("/api/blocks")
            // assert
            .then()
            .statusCode(HttpStatus.BAD_REQUEST.value())
    }

    @Test
    fun `it updates an exercise block`() {
        // arrange
        val exercise: Exercise = exerciseService.create(
            Exercise(
                name = "some exercise",
                description = "some exercise!"
            )
        )

        val oldRepetitionsQuantity: Int = 1
        val exerciseRepetition: ExerciseRepetition = ExerciseRepetition(
            exercise = exercise,
            quantity = oldRepetitionsQuantity
        )

        val blockName: String = "block to create"
        val blockNotes: String = "block notes to create"
        val newExerciseBlock: ExerciseBlock = exerciseBlockService.create(
            ExerciseBlock(
                name = blockName,
                exercises = listOf(exerciseRepetition),
                notes = blockNotes
            ))

        // updated exercise
        val updatedRepetitionsQuantity = oldRepetitionsQuantity+1
        val updatedExerciseRepetition: ExerciseRepetition = ExerciseRepetition(
            exercise = exercise,
            quantity = updatedRepetitionsQuantity
        )

        val anotherExercise: Exercise = exerciseService.create(
            Exercise(
                name = "some other exercise",
                description = "some other exercise!"
            )
        )
        val anotherExerciseRepetition: ExerciseRepetition = ExerciseRepetition(
            exercise = anotherExercise,
            quantity = updatedRepetitionsQuantity
        )

        val updatedExerciseBlockName: String = "updated $blockName"
        val updatedExerciseBlockNotes: String = "updated $blockNotes"
        val updatedExerciseBlock: ExerciseBlock = ExerciseBlock(
            id = newExerciseBlock.id,
            name = updatedExerciseBlockName,
            exercises = listOf(updatedExerciseRepetition, anotherExerciseRepetition),
            notes = updatedExerciseBlockNotes
        )

        // act
        val exerciseBlockId: Int = RestAssured.given()
            .body(updatedExerciseBlock)
            .contentType(ContentType.JSON)
            .put("/api/blocks")
            // assert
            .then()
            .statusCode(HttpStatus.OK.value())
            .body("name", Matchers.equalTo(updatedExerciseBlockName))
            .body("notes", Matchers.equalTo(updatedExerciseBlockNotes))
            .body("exercises.size()", Matchers.equalTo(2))
            .body("exercises[0].quantity", Matchers.equalTo(updatedExerciseRepetition.quantity))
            .body("exercises[0].exercise.id", Matchers.equalTo(updatedExerciseRepetition.exercise.id))
            .body("exercises[1].quantity", Matchers.equalTo(anotherExerciseRepetition.quantity))
            .body("exercises[1].exercise.id", Matchers.equalTo(anotherExerciseRepetition.exercise.id))
            .extract()
            .path("id")

        // remove the exercise
        exerciseBlockService.delete(exerciseBlockId)
        exerciseService.delete(exercise.id!!)
        exerciseService.delete(anotherExercise.id!!)
    }

    @Test
    fun `it returns bad request when updating an exercise block without id`() {
        // arrange
        val notCreatedExerciseBlock: ExerciseBlock = ExerciseBlock(
            name = "this one doesn't exist on the database",
            exercises = listOf(),
            notes = "this will return a bad request"
        )

        // act
        RestAssured.given()
            .body(notCreatedExerciseBlock)
            .contentType(ContentType.JSON)
            .put("/api/blocks")
            // assert
            .then()
            .statusCode(HttpStatus.BAD_REQUEST.value())
    }

    @Test
    fun `it deletes an exercise block`() {
        // arrange
        val exercise: Exercise = exerciseService.create(
            Exercise(
                name = "some exercise",
                description = "some exercise!"
            )
        )

        val oldRepetitionsQuantity: Int = 1
        val exerciseRepetition: ExerciseRepetition = ExerciseRepetition(
            exercise = exercise,
            quantity = oldRepetitionsQuantity
        )

        val blockName: String = "block to create"
        val blockNotes: String = "block notes to create"
        val exerciseBlockToDelete: ExerciseBlock = exerciseBlockService.create(
            ExerciseBlock(
                name = blockName,
                exercises = listOf(exerciseRepetition),
                notes = blockNotes
            ))

        // act
        RestAssured.given()
            .delete("/api/blocks/{id}", exerciseBlockToDelete.id)
            // assert
            .then()
            .statusCode(HttpStatus.NO_CONTENT.value())

        assertThat(exerciseBlockService.getById(exerciseBlockToDelete.id!!)).isNull()
        exerciseService.delete(exercise.id!!)
    }
}