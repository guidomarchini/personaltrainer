package unq.edu.remotetrainer.persistence.entity

import org.joda.time.LocalDate
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id

@Entity
class ExerciseTrackingEntity (
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Int? = null,

    var quantity: Int,

    var date: LocalDate
)
