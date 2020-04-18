package unq.edu.remotetrainer.persistence.entity

import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.Id

@Entity
class ExerciseEntity (
    @Id @GeneratedValue
    var id: Int? = null,

    var name: String,

    var description: String,

    var link: String? = null
)