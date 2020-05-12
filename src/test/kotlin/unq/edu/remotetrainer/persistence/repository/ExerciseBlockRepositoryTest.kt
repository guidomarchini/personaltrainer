package unq.edu.remotetrainer.persistence.repository

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.AfterAll
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager
import org.springframework.data.repository.findByIdOrNull
import unq.edu.remotetrainer.persistence.entity.ExerciseBlockEntity
import unq.edu.remotetrainer.persistence.entity.ExerciseEntity
import unq.edu.remotetrainer.persistence.entity.ExerciseRepetitionEntity

@DataJpaTest
class ExerciseBlockRepositoryTest  @Autowired constructor(
    val entityManager: TestEntityManager,
    val exerciseBlockRepository: ExerciseBlockRepository
){

    @AfterAll
    fun tearDown() {
        exerciseBlockRepository.deleteAll()
    }

    @Test
    fun `it creates an exercise block`() {
        // arrange
        val exerciseBlock: ExerciseBlockEntity =
            ExerciseBlockEntity(
                exercises = listOf(),
                notes = ""
            )

        // act
        val savedInstance: ExerciseBlockEntity =
            exerciseBlockRepository.save(exerciseBlock)

        // assert
        assertThat(savedInstance.id).isNotNull()
    }

    @Test
    fun `it creates an exercise block with exercise repetitions`() {
        // arrange
        val exercise: ExerciseEntity =
            entityManager.persist(ExerciseEntity(
                name = "some exercise",
                description = "some description"
            ))

        val exerciseRepetition: ExerciseRepetitionEntity =
            entityManager.persist(ExerciseRepetitionEntity(
                exercise = exercise,
                quantity = 1
            ))

        entityManager.flush()

        val exerciseBlock: ExerciseBlockEntity =
            ExerciseBlockEntity(
                exercises = listOf(exerciseRepetition),
                notes = ""
            )

        // act
        val savedInstance: ExerciseBlockEntity =
            exerciseBlockRepository.save(exerciseBlock)

        // assert
        assertThat(savedInstance.id).isNotNull()
        assertThat(savedInstance.exercises.size).isEqualTo(1)
    }

    @Test
    fun `it returns only the blocks with non null names`() {
        // arrange
        val namedBlock: ExerciseBlockEntity =
            entityManager.persist(ExerciseBlockEntity(
                name = "this block has to be returned",
                exercises = listOf(),
                notes = ""
            ))

        val unnamedBlock: ExerciseBlockEntity =
            entityManager.persist(ExerciseBlockEntity(
                exercises = listOf(),
                notes = ""
            ))

        entityManager.flush()

        // act
        val namedBlocks: List<ExerciseBlockEntity> =
            exerciseBlockRepository.getAllByNameNotNull().toList()

        // assert
        assertThat(namedBlocks.size).isEqualTo(1)
        assertThat(namedBlocks.find { it.id == namedBlock.id }).isNotNull()
        assertThat(namedBlocks.find { it.id == unnamedBlock.id }).isNull()
    }

    @Test
    fun `it updates the instance`() {
        // arrange
        val exerciseBlock: ExerciseBlockEntity =
            entityManager.persist(ExerciseBlockEntity(
                name = "some name",
                exercises = listOf(),
                notes = ""
            ))

        val updatedName: String = "some new name"
        val updatedNotes: String = "some notes"

        // act
        val found: ExerciseBlockEntity? = exerciseBlockRepository.findByIdOrNull(exerciseBlock.id!!)
        val nonNullEntity: ExerciseBlockEntity = checkNotNull(found)

        nonNullEntity.name = updatedName
        nonNullEntity.notes = updatedNotes

        exerciseBlockRepository.save(nonNullEntity)

        val updatedEntity: ExerciseBlockEntity? = exerciseBlockRepository.findByIdOrNull(exerciseBlock.id!!)

        // assert
        assertThat(updatedEntity).isNotNull()
        updatedEntity!!
        assertThat(updatedEntity.name).isEqualTo(updatedName)
        assertThat(updatedEntity.notes).isEqualTo(updatedNotes)
    }

    @Test
    fun `it removes an Exercise`() {
        // arrange
        val exerciseBlock: ExerciseBlockEntity =
            entityManager.persist(ExerciseBlockEntity(
                name = "some name",
                exercises = listOf(),
                notes = ""
            ))

        // act
        exerciseBlockRepository.deleteById(exerciseBlock.id!!)

        // assert
        assertThat(exerciseBlockRepository.findByIdOrNull(exerciseBlock.id!!)).isNull()
    }
}