package unq.edu.remotetrainer.persistence.entity

import javax.persistence.*

@Entity
class ExerciseBlockEntity (
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Int? = null,

    var name: String? = null,

    @OneToMany(targetEntity = ExerciseRepetitionEntity::class)
    var exercises: List<ExerciseRepetitionEntity>,

    var notes: String
)