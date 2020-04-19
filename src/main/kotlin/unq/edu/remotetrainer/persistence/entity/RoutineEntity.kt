package unq.edu.remotetrainer.persistence.entity

import org.joda.time.LocalDate
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.Id
import javax.persistence.OneToMany

@Entity
class RoutineEntity (
    @Id @GeneratedValue
    var id: Int?,

    var date: LocalDate,

    @OneToMany(targetEntity = ExerciseBlockEntity::class)
    var exerciseBlocks: List<ExerciseBlockEntity>,

    var notes: String
)