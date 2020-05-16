package unq.edu.remotetrainer.persistence.entity

import javax.persistence.*

@Entity
class TrackingEntity (
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Int? = null,

    @ManyToOne
    var exercise: ExerciseEntity,

    @OneToMany(targetEntity = ExerciseTrackingEntity::class)
    var exerciseTrackings: List<ExerciseTrackingEntity>
)