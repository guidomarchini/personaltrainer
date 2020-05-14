package unq.edu.remotetrainer.application.rest.exception

class ExerciseNotFoundException(idNotFound: Int): EntityNotFoundException("Excercise", idNotFound)

class TrackingNotFoundException(idNotFound: Int): EntityNotFoundException("Tracking", idNotFound)

class ExerciseBlockNotFoundException(idNotFound: Int): EntityNotFoundException("ExerciseBlock", idNotFound)

open class EntityNotFoundException(entityNotFound: String, idNotFound: Int): Exception("$entityNotFound with id $idNotFound not found")

class ValidationError(message: String): Exception(message)