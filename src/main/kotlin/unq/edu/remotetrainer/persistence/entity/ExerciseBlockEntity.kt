package unq.edu.remotetrainer.persistence.entity

import javax.persistence.*

@Entity
class ExerciseBlockEntity (
    @Id @GeneratedValue
    var id: Int?,

    @OneToMany(targetEntity = ExerciseRepetitionEntity::class)
    var exercises: Collection<ExerciseRepetitionEntity>,

    var notes: String
)