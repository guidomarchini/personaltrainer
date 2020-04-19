package unq.edu.remotetrainer.persistence.entity

import javax.persistence.*

@Entity
class TrackingEntity (
    @Id @GeneratedValue
    var id: Int?,

    @OneToMany(targetEntity = ExerciseEntity::class)
    var exerciseTrackings: Collection<ExerciseTrackingEntity>,

    var favourite: Boolean
)