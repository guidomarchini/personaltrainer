package unq.edu.remotetrainer.application.controller.exceptions

class ExerciseNotFoundException(idNotFound: Int): Exception("Excercise with id $idNotFound not found.")