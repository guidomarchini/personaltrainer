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
    fun `When findNyIdOrNull then returns an Excercise`() {
        // arrange
        val pullups: ExerciseEntity = ExerciseEntity(
            name = "Pullups",
            description = "They're hard!"
        )
        entityManager.persist(pullups)
        entityManager.flush()

        // act
        val found = exerciseRepository.findByIdOrNull(pullups.id!!)

        // assert
        assertThat(found).isEqualTo(pullups)
    }
}