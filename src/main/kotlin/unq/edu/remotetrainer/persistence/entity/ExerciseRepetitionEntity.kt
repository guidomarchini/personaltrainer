package unq.edu.remotetrainer.persistence.entity

import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.Id
import javax.persistence.ManyToOne

@Entity
class ExerciseRepetitionEntity (
    @Id @GeneratedValue
    var id: Int?,

    @ManyToOne
    var exercise: ExerciseEntity,

    var quantity: Int
)