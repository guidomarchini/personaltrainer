package unq.edu.remotetrainer.application.rest.exceptions

class ExerciseNotFoundException(idNotFound: Int): Exception("Excercise with id $idNotFound not found.")

class TrackingNotFoundException(idNotFound: Int): Exception("Tracking with id $idNotFound not found.")