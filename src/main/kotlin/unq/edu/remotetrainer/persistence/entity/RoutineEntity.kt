package unq.edu.remotetrainer.persistence.entity

import org.joda.time.LocalDate
import javax.persistence.*

@Entity
class RoutineEntity (
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Int? = null,

    var date: LocalDate,

    var shortDescription: String,

    @OneToMany(targetEntity = ExerciseBlockEntity::class)
    var exerciseBlocks: MutableList<ExerciseBlockEntity>,

    var notes: String
)