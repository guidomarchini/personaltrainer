package unq.edu.remotetrainer.api

/**
 * A {@link TrackingProfile} is used to keep the tracking of the clients, or self.
 * Multiple profiles can be stored in the app.
 */
data class TrackingProfile (
    val id: Int?,
    val name: String,
    val trackings: List<Tracking>
)