package unq.edu.remotetrainer.persistence.repository

import org.assertj.core.api.Assertions.assertThat
import org.joda.time.LocalDate
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager
import org.springframework.data.repository.findByIdOrNull
import unq.edu.remotetrainer.persistence.entity.ExerciseBlockEntity
import unq.edu.remotetrainer.persistence.entity.RoutineEntity

@DataJpaTest
internal class RoutineRepositoryTest @Autowired constructor(
    override val entityManager: TestEntityManager,
    override val repository: RoutineRepository,
    val exerciseBlockRepository: ExerciseBlockRepository
): AbstractRepositoryTest<RoutineEntity>() {

    @AfterEach
    override fun afterEach() {
        repository.deleteAll()
        exerciseBlockRepository.deleteAll()
    }


    @Test
    fun `it updates the instance`() {
        // arrange
        val routine =
            entityManager.persistAndFlush(sampleEntity())

        val exerciseBlockEntity: ExerciseBlockEntity =
            entityManager.persistAndFlush(
                ExerciseBlockEntity(
                notes = "",
                exercises = mutableListOf()
            ))

        val updatedNotes: String = "updated routine notes"
        val updatedDate: LocalDate = LocalDate().plusDays(1)

        // act
        val savedEntity: RoutineEntity? = repository.findByIdOrNull(routine.id!!)
        val nonNullEntity: RoutineEntity = checkNotNull(savedEntity)

        nonNullEntity.notes = updatedNotes
        nonNullEntity.date = updatedDate
        nonNullEntity.exerciseBlocks.add(exerciseBlockEntity)

        repository.save(nonNullEntity)

        // arrange
        val updatedEntity: RoutineEntity? = repository.findByIdOrNull(routine.id!!)
        assertThat(updatedEntity).isNotNull()
        updatedEntity!!
        assertThat(updatedEntity.notes).isEqualTo(updatedNotes)
        assertThat(updatedEntity.date).isEqualTo(updatedDate)
        assertThat(updatedEntity.exerciseBlocks).hasSize(2)
        assertThat(updatedEntity.exerciseBlocks.find { it.id == exerciseBlockEntity.id }).isNotNull
    }

    /* ***************** *
     * INHERITED METHODS *
     * ***************** */
    override fun newInstanceAssertions(savedInstance: RoutineEntity, newInstance: RoutineEntity) {
        assertThat(newInstance.date).isEqualTo(savedInstance.date)
        assertThat(newInstance.exerciseBlocks).isEqualTo(savedInstance.exerciseBlocks)
        assertThat(newInstance.notes).isEqualTo(savedInstance.notes)
    }

    override fun id(entity: RoutineEntity): Int {
        return entity.id!!
    }

    override fun sampleEntity(): RoutineEntity {
        val exerciseBlock: ExerciseBlockEntity =
            entityManager.persistAndFlush(ExerciseBlockEntity(
                name = "sample exercise block",
                notes = "",
                exercises = mutableListOf()
            ))

        return RoutineEntity(
            date = LocalDate(),
            notes = "just some sample notes",
            exerciseBlocks = mutableListOf(exerciseBlock)
        )
    }
}