package unq.edu.remotetrainer.persistence.repository

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager
import org.springframework.data.repository.findByIdOrNull
import unq.edu.remotetrainer.persistence.entity.ExerciseEntity

@DataJpaTest
internal class ExerciseRepositoryTest @Autowired constructor(
    override val entityManager: TestEntityManager,
    override val repository: ExerciseRepository
): AbstractRepositoryTest<ExerciseEntity>() {

    @Test
    fun `it gets by name`() {
        // arrange
        val name: String = "Pullups"
        val pullups: ExerciseEntity =
            entityManager.persistAndFlush(ExerciseEntity(
                name = name,
                description = "They're hard!"
            ))

        // act
        val found = repository.getByName(name)

        // assert
        assertThat(found).isNotNull()
        assertThat(found).isEqualTo(pullups)
    }

    @Test
    fun `it updates the instance`() {
        // arrange
        val oldDescription: String = "They're hard!"
        val newDescription: String = "They're hard, but everything becomes easy with practice!"
        val pullups: ExerciseEntity =
            entityManager.persistAndFlush(ExerciseEntity(
                name = "Pullups",
                description = oldDescription
            ))

        // act
        val found = repository.findByIdOrNull(pullups.id!!)
        val nonNullEntity: ExerciseEntity = checkNotNull(found)
        nonNullEntity.description = newDescription

        val updatedEntity = repository.findByIdOrNull(pullups.id!!)

        // assert
        assertThat(updatedEntity).isNotNull()
        assertThat(updatedEntity!!.description).isEqualTo(newDescription)
    }



    /* ***************** *
     * INHERITED METHODS *
     * ***************** */
    override fun newInstanceAssertions(savedInstance: ExerciseEntity, newInstance: ExerciseEntity) {
        assertThat(savedInstance.id).isNotNull()
        assertThat(savedInstance.name).isEqualTo(newInstance.name)
        assertThat(savedInstance.description).isEqualTo(newInstance.description)
    }

    override fun id(entity: ExerciseEntity): Int {
        return entity.id!!
    }

    override fun sampleEntity(): ExerciseEntity {
        return ExerciseEntity(
            name = "Pullups",
            description = "They're hard!"
        )
    }
}