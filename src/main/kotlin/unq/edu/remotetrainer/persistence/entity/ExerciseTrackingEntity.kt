package unq.edu.remotetrainer.persistence.entity

import org.joda.time.LocalDate
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.Id
import javax.persistence.ManyToOne

@Entity
class ExerciseTrackingEntity (
    @Id
    @GeneratedValue
    var id: Int?,

    @ManyToOne
    var exercise: ExerciseEntity,

    var quantity: Int,

    var date: LocalDate
)
