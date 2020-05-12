package unq.edu.remotetrainer.application.sevice

import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.data.repository.CrudRepository
import unq.edu.remotetrainer.mapper.RemoteTrainerMapper
import java.util.*

abstract class AbstractServiceTest<M, E> {
    abstract val mapper: RemoteTrainerMapper<M, E>
    abstract val repository: CrudRepository<E, Int>

    abstract val modelObject: M
    abstract val entityObject: E

    abstract val service: RemoteTrainerService<M, E>

    @BeforeEach
    fun before() {
        whenever(mapper.toEntity(modelObject)).thenReturn(entityObject)
        whenever(mapper.toModel(entityObject)).thenReturn(modelObject)
    }


    @Test
    fun `creates an instance`() {
        // arrange
        whenever(repository.save(entityObject)).thenReturn(entityObject)

        // act
        service.create(modelObject)

        // assert
        verify(mapper).toEntity(modelObject)
        verify(repository).save(entityObject)
        verify(mapper).toModel(entityObject)
    }

    @Test
    fun `it gets all instances`() {
        // arrange
        whenever(repository.findAll())
            .thenReturn(listOf(entityObject))

        // act
        val result: List<M> =
            service.getAll()

        // assert
        verify(mapper).toModel(entityObject)
        Assertions.assertEquals(1, result.size)
        assertThat(modelObject).isEqualTo(result.first())
    }

    @Test
    fun `it gets exercise by id`() {
        // arrange
        val id: Int = 0
        whenever(repository.findById(id))
            .thenReturn(Optional.of(entityObject))

        // act
        val result: M? =
            service.getById(id)

        // assert
        assertThat(modelObject).isEqualTo(result)
    }

    @Test
    fun `updates an instance`() {
        // arrange
        whenever(repository.save(entityObject)).thenReturn(entityObject)

        // act
        service.update(modelObject)

        // assert
        verify(mapper).toEntity(modelObject)
        verify(repository).save(entityObject)
        verify(mapper).toModel(entityObject)
    }

    @Test
    fun `it deletes an instance`() {
        // arrange
        val id: Int = 0

        // act
        service.delete(id)

        // assert
        verify(repository).deleteById(id)
    }
}