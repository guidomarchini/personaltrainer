package unq.edu.remotetrainer.mapper

interface RemoteTrainerMapper<M, E> {
    /**
     * Transforms a model object to an entity object
     */
    fun toEntity(model: M): E

    /**
     * Transforms an entity object to a model object
     */
    fun toModel(entity: E): M
}