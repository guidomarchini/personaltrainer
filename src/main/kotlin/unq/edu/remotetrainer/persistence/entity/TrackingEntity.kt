package unq.edu.remotetrainer.persistence.entity

import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.Id
import javax.persistence.ManyToOne

@Entity
class TrackingEntity (
    @Id @GeneratedValue
    var id: Int?,

    @ManyToOne
    var exercise: ExerciseEntity,

    var quantity: Int,

    var favourite: Boolean
)