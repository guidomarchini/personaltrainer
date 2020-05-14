package unq.edu.remotetrainer.application.rest

import io.restassured.RestAssured
import io.restassured.http.ContentType
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
import unq.edu.remotetrainer.model.ExerciseBlock

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
internal class ExerciseBlockControllerTest (
    @Autowired val exerciseBlockService: ExerciseBlockService,
    @Autowired val exerciseService: ExerciseService,
    @LocalServerPort val port: Int
) {

    var exerciseBlock: ExerciseBlock = ExerciseBlock(
        name = "example block",
        exercises = listOf(),
        notes = "example block"
    )

    @BeforeAll
    fun beforeAll() {
        RestAssured.port = port
        exerciseBlock = exerciseBlockService.create(exerciseBlock)
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
        val blockName = "block to create"
        val blockNotes = "block notes to create"
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
            .extract()
            .path("id")

        // remove the exercise
        exerciseBlockService.delete(newExerciseBlockId)
    }

//    @Test
//    fun `it creates`

}