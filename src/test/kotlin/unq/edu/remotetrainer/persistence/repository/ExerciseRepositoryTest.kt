package unq.edu.remotetrainer.persistence.repository

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager
import org.springframework.data.repository.findByIdOrNull
import unq.edu.remotetrainer.persistence.entity.ExerciseEntity

@DataJpaTest
class ExerciseRepositoryTest @Autowired constructor(
    val entityManager: TestEntityManager,
    val exerciseRepository: ExerciseRepository
){
    @Test
    fun `It creates an Exercise`() {
        // arrange
        val pullups: ExerciseEntity = ExerciseEntity(
            name = "Pullups",
            description = "They're hard!"
        )

        // act
        val savedInstance: ExerciseEntity = exerciseRepository.save(pullups)

        // assert
        assertThat(savedInstance.id).isNotNull()
        assertThat(savedInstance.name).isEqualTo(pullups.name)
        assertThat(savedInstance.description).isEqualTo(pullups.description)
    }

    @Test
    fun `it returns a saved Exercise`() {
        // arrange
        val pullups: ExerciseEntity = ExerciseEntity(
            name = "Pullups",
            description = "They're hard!"
        )
        entityManager.persistAndFlush(pullups)

        // act
        val found = exerciseRepository.findByIdOrNull(pullups.id!!)

        // assert
        assertThat(found).isEqualTo(pullups)
    }

    @Test
    fun `it returns all exercises with the name`() {
        // arrange
        val exerciseName = "Pullups"
        val pullups: ExerciseEntity = ExerciseEntity(
            name = exerciseName,
            description = "They're hard!"
        )
        val pullups2: ExerciseEntity = ExerciseEntity(
            name = exerciseName,
            description = "second exercise with same name"
        )
        val pullups3: ExerciseEntity = ExerciseEntity(
            name = "anther $exerciseName",
            description = "this one has different name"
        )
        entityManager.persist(pullups)
        entityManager.persist(pullups2)
        entityManager.persist(pullups3)
        entityManager.flush()

        // act
        val foundPullups: Iterable<ExerciseEntity> =
            exerciseRepository.getAllByName(exerciseName)

        // assert
        assertThat(foundPullups).hasSize(2)
        assertThat(foundPullups).doesNotContain(pullups3)
    }

    @Test
    fun `The instance is updated`() {
        // arrange
        val oldDescription: String = "They're hard!"
        val newDescription: String = "They're hard, but everything becomes easy with practice!"
        val pullups: ExerciseEntity = ExerciseEntity(
            name = "Pullups",
            description = oldDescription
        )
        entityManager.persistAndFlush(pullups)

        // act
        val found = exerciseRepository.findByIdOrNull(pullups.id!!)
        val nonNullEntity: ExerciseEntity = checkNotNull(found)
        nonNullEntity.description = newDescription

        val updatedEntity = exerciseRepository.findByIdOrNull(pullups.id!!)

        // assert
        assertThat(updatedEntity).isNotNull()
        assertThat(updatedEntity!!.description).isEqualTo(newDescription)
    }

    @Test
    fun `It removes an Exercise`() {
        // arrange
        val pullups: ExerciseEntity = ExerciseEntity(
            name = "Pullups",
            description = "They're hard!"
        )
        entityManager.persistAndFlush(pullups)

        // act
        exerciseRepository.deleteById(pullups.id!!)

        // assert
        assertThat(exerciseRepository.findByIdOrNull(pullups.id!!)).isNull()
    }


    // custom queries
    @Test
   fun `It gets by name`() {
        // arrange
        val name: String = "Pullups"
        val pullups: ExerciseEntity = ExerciseEntity(
            name = name,
            description = "They're hard!"
        )
        entityManager.persistAndFlush(pullups)

        // act
        val found = exerciseRepository.getByName(name)

        // assert
        assertThat(found).isNotNull()
        assertThat(found).isEqualTo(pullups)
    }

    @Test
    fun `It gets by id`() {
        // arrange
        val pullups: ExerciseEntity = ExerciseEntity(
            name = "Pullups",
            description = "They're hard!"
        )
        entityManager.persistAndFlush(pullups)

        // act
        val found = exerciseRepository.getById(pullups.id!!)

        // assert
        assertThat(found).isNotNull()
        assertThat(found).isEqualTo(pullups)
    }

    @Test
    fun `It gets all by name`() {
        // arrange
        val pullupsName: String = "Pullups"
        val pullups: ExerciseEntity = ExerciseEntity(
            name = pullupsName,
            description = "They're hard!"
        )
        entityManager.persistAndFlush(pullups)

        val pushupsname: String = "Pushups"
        val pushups: ExerciseEntity = ExerciseEntity(
            name = pushupsname,
            description = "Calisthenics basic"
        )
        entityManager.persistAndFlush(pushups)

        // act
        val found = exerciseRepository.getExerciseEntitiesByNameIn(listOf(pullupsName, pushupsname))

        // assert
        assertThat(found).isNotEmpty()
        assertThat(found).hasSize(2)
        assertThat(found).contains(pullups, pushups)
    }
}