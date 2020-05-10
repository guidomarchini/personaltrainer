package unq.edu.remotetrainer.persistence.entity

import javax.persistence.*

@Entity
class TrackingProfileEntity (
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Int? = null,

    var name: String,

    @OneToMany(targetEntity = TrackingEntity::class)
    var trackings: List<TrackingEntity>
)