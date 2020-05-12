package unq.edu.remotetrainer.application.sevice

import org.springframework.data.repository.CrudRepository
import org.springframework.data.repository.findByIdOrNull
import unq.edu.remotetrainer.mapper.RemoteTrainerMapper

interface RemoteTrainerService<M, E> {
    val repository: CrudRepository<E, Int>
    val mapper: RemoteTrainerMapper<M, E>

    fun create(modelObject: M): M {
        return createOrUpdate(modelObject)
    }

    fun getAll() : List<M> {
        return repository.findAll().map {
            mapper.toModel(it)
        }
    }

    fun getById(id: Int): M? {
        return repository.findByIdOrNull(id)
            ?.let { mapper.toModel(it) }
    }

    fun update(modelObject: M): M {
        return createOrUpdate(modelObject)
    }

    fun delete(id: Int): Unit {
        repository.deleteById(id)
    }

    private fun createOrUpdate(modelObject: M): M {
        return mapper.toModel(
            repository.save(
                mapper.toEntity(modelObject)
            )
        )
    }
}