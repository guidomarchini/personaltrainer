package unq.edu.remotetrainer.persistence.repository

import org.assertj.core.api.Assertions
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Test
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager
import org.springframework.data.repository.CrudRepository
import org.springframework.data.repository.findByIdOrNull

/**
 * Abstract Repository test.
 * It contains the basic tests for basic CRUD methods.
 * The implementations should define the update operation, along with the custom repository's methods.
 * The repository is cleaned after each test, to maintain consistency on further tests.
 * If instances are created inside the test context, be sure to override afterEach method and delete them.
 * Generics:
 *  * E = Entity object.
 */
@DataJpaTest
abstract class AbstractRepositoryTest<E> {
    abstract val entityManager: TestEntityManager
    abstract val repository: CrudRepository<E, Int> // for now, Int as id is ok

    @AfterEach
    fun afterEach() {
        repository.deleteAll()
    }

    @Test
    fun `it creates an instance`() {
        val newInstance: E = sampleEntity()

        // act
        val savedInstance: E = repository.save(newInstance)

        // assert
        newInstanceAssertions(savedInstance, newInstance)
    }

    @Test
    fun `it returns a saved entity`() {
        // arrange
        val entity: E = entityManager.persistAndFlush(sampleEntity())

        // act
        val found = repository.findByIdOrNull(id(entity))

        // assert
        Assertions.assertThat(found).isEqualTo(entity)
    }

    @Test
    fun `it removes an entity`() {
        // arrange
        val entity: E = entityManager.persistAndFlush(sampleEntity())
        val entityId: Int = id(entity)

        // act
        repository.deleteById(entityId)

        // assert
        Assertions.assertThat(repository.findByIdOrNull(entityId)).isNull()
    }

    /** Assertions to be done for a new instance */
    abstract fun newInstanceAssertions(savedInstance: E, newInstance: E)

    /** Returns the Entity id */
    abstract fun id(entity: E): Int

    /** Creates a new sample Entity to perform basic CRUD operations */
    abstract fun sampleEntity(): E
}