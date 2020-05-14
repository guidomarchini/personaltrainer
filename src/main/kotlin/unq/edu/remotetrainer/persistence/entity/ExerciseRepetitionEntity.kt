package unq.edu.remotetrainer.persistence.entity

import javax.persistence.Embeddable
import javax.persistence.ManyToOne

@Embeddable
class ExerciseRepetitionEntity (
    @ManyToOne
    var exercise: ExerciseEntity,

    var quantity: Int
)