package unq.edu.remotetrainer.application.dto

import org.joda.time.LocalDate
import unq.edu.remotetrainer.model.Routine

data class RoutineDay (
    val dateDay: String,
    val dateNumber: Int,
    val date: LocalDate,
    val routines: List<Routine>
)