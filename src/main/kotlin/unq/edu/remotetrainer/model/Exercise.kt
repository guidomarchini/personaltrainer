package unq.edu.remotetrainer.model

/**
 * The exercise is the main focus of the application.
 * It has a name, with a description and a possible video link.
 */
data class Exercise (
    val id: Int? = null,
    val name: String,
    val description: String,
    val link: String? = null
)