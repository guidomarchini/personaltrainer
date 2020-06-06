package unq.edu.remotetrainer.application.dto

import org.joda.time.LocalDate
import unq.edu.remotetrainer.application.LocaleHelper
import unq.edu.remotetrainer.model.Routine

data class RoutineDay (
    val date: LocalDate,
    val routines: List<Routine>
) {
    fun dateDay(): String {
        return date.dayOfWeek().getAsText(LocaleHelper.locale)
    }

    fun dateNumber(): Int {
        return date.dayOfMonth;
    }
}