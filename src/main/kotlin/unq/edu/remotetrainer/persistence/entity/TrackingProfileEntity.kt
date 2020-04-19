package unq.edu.remotetrainer.persistence.entity

import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.Id
import javax.persistence.OneToMany

@Entity
class TrackingProfileEntity (
    @Id @GeneratedValue
    var id: Int?,

    var name: String,

    @OneToMany(targetEntity = TrackingEntity::class)
    var trackings: Collection<TrackingEntity>
)