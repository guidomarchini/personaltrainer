package unq.edu.remotetrainer.persistence.entity

import javax.persistence.*

@Entity
class ExerciseBlockEntity (
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Int? = null,

    var name: String? = null,

    @ElementCollection(targetClass = ExerciseRepetitionEntity::class)
    @CollectionTable(
        name = "exercise_repetition_entity",
        joinColumns = [JoinColumn(name="exercise_block")]
    )
    // JPA utilizes .remove, so this list should be mutable
    var exercises: MutableList<ExerciseRepetitionEntity>,

    var notes: String
)