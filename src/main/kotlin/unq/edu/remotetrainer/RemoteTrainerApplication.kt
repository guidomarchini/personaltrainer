package unq.edu.remotetrainer

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

/**
 * Main class, used to start the application
 */
@SpringBootApplication
class RemoteTrainerApplication

fun main(args: Array<String>) {
	runApplication<RemoteTrainerApplication>(*args)
}
