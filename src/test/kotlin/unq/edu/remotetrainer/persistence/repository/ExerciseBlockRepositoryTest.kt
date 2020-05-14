package unq.edu.remotetrainer.persistence.repository

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager
import org.springframework.data.repository.findByIdOrNull
import unq.edu.remotetrainer.persistence.entity.ExerciseBlockEntity
import unq.edu.remotetrainer.persistence.entity.ExerciseEntity
import unq.edu.remotetrainer.persistence.entity.ExerciseRepetitionEntity

@DataJpaTest
internal class ExerciseBlockRepositoryTest @Autowired constructor(
    override val entityManager: TestEntityManager,
    override val repository: ExerciseBlockRepository,
    val exerciseRepository: ExerciseRepository // needed to remove the created exercise
): AbstractRepositoryTest<ExerciseBlockEntity>() {

    override fun afterEach() {
        exerciseRepository.deleteAll()
        repository.deleteAll()
    }


    @Test
    fun `it returns only the blocks with non null names`() {
        // arrange
        val namedBlock: ExerciseBlockEntity =
            entityManager.persist(ExerciseBlockEntity(
                name = "this block has to be returned",
                exercises = mutableListOf(),
                notes = ""
            ))

        val unnamedBlock: ExerciseBlockEntity =
            entityManager.persist(ExerciseBlockEntity(
                exercises = mutableListOf(),
                notes = ""
            ))

        entityManager.flush()

        // act
        val namedBlocks: List<ExerciseBlockEntity> =
            repository.getAllByNameNotNull().toList()

        // assert
        assertThat(namedBlocks.size).isEqualTo(1)
        assertThat(namedBlocks.find { it.id == namedBlock.id }).isNotNull()
        assertThat(namedBlocks.find { it.id == unnamedBlock.id }).isNull()
    }

    @Test
    fun `it updates the instance`() {
        // arrange
        val exerciseBlock: ExerciseBlockEntity =
            entityManager.persistAndFlush(ExerciseBlockEntity(
                name = "some name",
                exercises = mutableListOf(),
                notes = ""
            ))

        val updatedName: String = "some new name"
        val updatedNotes: String = "some notes"

        // act
        val found: ExerciseBlockEntity? = repository.findByIdOrNull(exerciseBlock.id!!)
        val nonNullEntity: ExerciseBlockEntity = checkNotNull(found)

        nonNullEntity.name = updatedName
        nonNullEntity.notes = updatedNotes

        repository.save(nonNullEntity)

        val updatedEntity: ExerciseBlockEntity? = repository.findByIdOrNull(exerciseBlock.id!!)

        // assert
        assertThat(updatedEntity).isNotNull()
        updatedEntity!!
        assertThat(updatedEntity.name).isEqualTo(updatedName)
        assertThat(updatedEntity.notes).isEqualTo(updatedNotes)
    }

    @Test
    fun `it updates the instance with a change in repetitions`() {
        // arrange
        // base exerciseBlock
        val exercise: ExerciseEntity =
            entityManager.persist(ExerciseEntity(
                name = "some exercise",
                description = "some description"
            ))

        val exerciseRepetition: ExerciseRepetitionEntity =
            ExerciseRepetitionEntity(
                exercise = exercise,
                quantity = 1
            )

        val exerciseBlock: ExerciseBlockEntity =
            entityManager.persist(ExerciseBlockEntity(
                name = "some name",
                exercises = mutableListOf(),
                notes = ""
            ))

        entityManager.flush()

        // now, the update part
        exerciseBlock.exercises = mutableListOf(exerciseRepetition)

        // act
        repository.save(exerciseBlock)

        val updatedEntity: ExerciseBlockEntity? = repository.findByIdOrNull(exerciseBlock.id!!)

        // assert
        assertThat(updatedEntity).isNotNull()
        updatedEntity!!

        assertThat(updatedEntity.exercises).hasSize(1)

        val firstExercise = updatedEntity.exercises.firstOrNull()
        assertThat(firstExercise).isNotNull()
        firstExercise!!
        assertThat(firstExercise.quantity).isEqualTo(exerciseRepetition.quantity)
        assertThat(firstExercise.exercise).isEqualTo(exercise)
    }

    @Test
    fun `it cascades exercise repetitions`() {
        // arrange
        // base exerciseBlock
        val exercise: ExerciseEntity =
            entityManager.persist(ExerciseEntity(
                name = "some exercise",
                description = "some description"
            ))

        val exerciseRepetition: ExerciseRepetitionEntity =
            ExerciseRepetitionEntity(
                exercise = exercise,
                quantity = 1
            )

        val exerciseBlock: ExerciseBlockEntity =
            entityManager.persist(ExerciseBlockEntity(
                name = "some name",
                exercises = mutableListOf(exerciseRepetition),
                notes = ""
            ))

        entityManager.flush()

        // now, the update part
        exerciseRepetition.quantity +=1

        // act
        repository.save(exerciseBlock)

        val updatedEntity: ExerciseBlockEntity? = repository.findByIdOrNull(exerciseBlock.id!!)

        // assert
        assertThat(updatedEntity).isNotNull()
        updatedEntity!!

        assertThat(updatedEntity.exercises).hasSize(1)
        val firstExercise = updatedEntity.exercises.firstOrNull()
        assertThat(firstExercise).isNotNull()
        firstExercise!!
        assertThat(firstExercise.quantity).isEqualTo(exerciseRepetition.quantity)
        assertThat(firstExercise.exercise).isEqualTo(exercise)
    }




    /* ***************** *
     * INHERITED METHODS *
     * ***************** */
    override fun newInstanceAssertions(savedInstance: ExerciseBlockEntity, newInstance: ExerciseBlockEntity) {
        assertThat(savedInstance.id).isNotNull()
        assertThat(savedInstance.name).isEqualTo(newInstance.name)
        assertThat(savedInstance.notes).isEqualTo(newInstance.notes)
        assertThat(savedInstance.exercises).hasSize(1)
    }

    override fun id(entity: ExerciseBlockEntity): Int {
        return entity.id!!
    }

    override fun sampleEntity(): ExerciseBlockEntity {
        val exercise: ExerciseEntity =
            entityManager.persistAndFlush(ExerciseEntity(
                name = "some exercise",
                description = "some description"
            ))

        val exerciseRepetition: ExerciseRepetitionEntity =
            ExerciseRepetitionEntity(
                exercise = exercise,
                quantity = 1
            )

        return ExerciseBlockEntity(
            exercises = mutableListOf(exerciseRepetition),
            notes = ""
        )
    }
}