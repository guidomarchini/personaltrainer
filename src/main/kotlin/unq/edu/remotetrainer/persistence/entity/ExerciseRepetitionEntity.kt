package unq.edu.remotetrainer.persistence.entity

import javax.persistence.*

@Entity
class ExerciseRepetitionEntity (
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Int? = null,

    @ManyToOne
    var exercise: ExerciseEntity,

    var quantity: Int
)