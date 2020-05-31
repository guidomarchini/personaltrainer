package unq.edu.remotetrainer.application.rest

import io.restassured.RestAssured
import io.restassured.http.ContentType
import io.restassured.response.ExtractableResponse
import io.restassured.response.Response
import org.assertj.core.api.Assertions.assertThat
import org.hamcrest.Matchers
import org.joda.time.LocalDate
import org.junit.jupiter.api.AfterAll

import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.web.server.LocalServerPort
import org.springframework.http.HttpStatus
import unq.edu.remotetrainer.application.sevice.ExerciseBlockService
import unq.edu.remotetrainer.application.sevice.RoutineService
import unq.edu.remotetrainer.model.ExerciseBlock
import unq.edu.remotetrainer.model.Routine

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
internal class RoutineControllerTest (
    @Autowired val routineService: RoutineService,
    @Autowired val exerciseBlockService: ExerciseBlockService,
    @LocalServerPort val port: Int
) {

    val routine: Routine = routineService.create(
        Routine(
            date = LocalDate(),
            shortDescription = "test routine",
            exerciseBlocks = listOf(),
            notes = "example block"
        )
    )

    @BeforeAll
    fun beforeAll() {
        RestAssured.port = port
    }

    @AfterAll
    fun afterAll() {
        routineService.delete(routine.id!!)
    }

    @Test
    fun `get routines`() {
        // arrange

        // act
        RestAssured.get("/api/routines")
            // assert
            .then()
            .statusCode(HttpStatus.OK.value())
            .body("size()", Matchers.equalTo(1))
            .body("find { it.id == ${routine.id} }", Matchers.notNullValue())
    }

    @Test
    fun `get routine by id`() {
        // arrange

        // act
        RestAssured.get("/api/routines/{id}", routine.id)
            // assert
            .then()
            .statusCode(HttpStatus.OK.value())
            .body("id", Matchers.equalTo(routine.id))
    }

    @Test
    fun `create a routine`() {
        // arrange
        val routineNotes = "some notes"
        val routineDate = LocalDate()
        val newRoutine: Routine =
            Routine(
                date = routineDate,
                shortDescription = "test routine",
                exerciseBlocks = listOf(),
                notes = routineNotes
            )

        // act
        val extractedResponse: ExtractableResponse<Response> = RestAssured.given()
            .body(newRoutine)
            .contentType(ContentType.JSON)
            .post("/api/routines")
            // assert
            .then()
            .statusCode(HttpStatus.CREATED.value())
            .body("notes", Matchers.equalTo(routineNotes))
            .body("exerciseBlocks.size()", Matchers.equalTo(0))
            .extract()

        val extractedDate: String = extractedResponse.path("date")
        assertThat(routineDate).isEqualTo(LocalDate.parse(extractedDate))

        // remove the routine
        val newRoutineId: Int = extractedResponse.path("id")
        routineService.delete(newRoutineId)
    }

    @Test
    fun `it updates a routine`() {
        // arrange
        val routineNotes = "some notes"
        val routineDate = LocalDate()
        val newRoutine: Routine = routineService.create(Routine(
            date = routineDate,
            shortDescription = "test routine",
            exerciseBlocks = listOf(),
            notes = routineNotes
        ))

        val exerciseBlock: ExerciseBlock = exerciseBlockService.create(
            ExerciseBlock(
                exercises = listOf(),
                notes = "block notes"
            ))

        // updated exercise
        val updatedNotes = "updated $routineNotes"
        val updatedDate = routineDate.plusDays(1)
        val updatedRoutine: Routine =
            Routine(
                id = newRoutine.id,
                date = updatedDate,
                shortDescription = "test routine",
                exerciseBlocks = listOf(exerciseBlock),
                notes = updatedNotes
            )

        // act
        val extractedResponse: ExtractableResponse<Response> = RestAssured.given()
            .body(updatedRoutine)
            .contentType(ContentType.JSON)
            .put("/api/routines")
            // assert
            .then()
            .statusCode(HttpStatus.OK.value())
            .body("notes", Matchers.equalTo(updatedNotes))
            .body("exerciseBlocks.size()", Matchers.equalTo(1))
            .extract()

        assertThat(updatedDate).isEqualTo(LocalDate.parse(extractedResponse.path("date")))

        // remove the new entities
        routineService.delete(newRoutine.id!!)
        exerciseBlockService.delete(exerciseBlock.id!!)
    }

    @Test
    fun `it returns bad request when updating a routine without id`() {
        // arrange
        val notCreatedRoutine: Routine =
            Routine(
                date = LocalDate(),
                shortDescription = "test routine",
                exerciseBlocks = listOf(),
                notes = "this will return a bad request"
            )

        // act
        RestAssured.given()
            .body(notCreatedRoutine)
            .contentType(ContentType.JSON)
            .put("/api/routines")
            // assert
            .then()
            .statusCode(HttpStatus.BAD_REQUEST.value())
    }

    @Test
    fun `it deletes a routine`() {
        // arrange
        val routineNotes = "some notes"
        val routineDate = LocalDate()
        val newRoutine: Routine = routineService.create(Routine(
            date = routineDate,
            shortDescription = "test routine",
            exerciseBlocks = listOf(),
            notes = routineNotes
        ))

        // act
        RestAssured.given()
            .delete("/api/routines/{id}", newRoutine.id)
            // assert
            .then()
            .statusCode(HttpStatus.NO_CONTENT.value())

        assertThat(routineService.getById(newRoutine.id!!)).isNull()
    }
}