package ru.dekabrsky.rfl.core

import java.time.LocalDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.time.format.DateTimeParseException

fun tryParseServerDate(dateTime: String?): LocalDateTime? {
    return try {
        val zonedDateTime = java.time.ZonedDateTime.parse(dateTime)
        zonedDateTime.withZoneSameInstant(ZoneId.systemDefault()).toLocalDateTime()
    } catch (e: DateTimeParseException) {
        null
    }
}

fun formatDateTime(dateTime: LocalDateTime): String {
    val now = LocalDateTime.now()
    val today = now.toLocalDate()
    val yesterday = today.minusDays(1)
    val date = dateTime.toLocalDate()

    return when {
        date == today -> "Сегодня, ${formatTimeOnly(dateTime)}"
        date == yesterday -> "Вчера, ${formatTimeOnly(dateTime)}"
        else -> "${formatDateOnly(dateTime)}, ${formatTimeOnly(dateTime)}"
    }
}

private fun formatDateOnly(dateTime: LocalDateTime): String {
    return DateTimeFormatter.ofPattern("dd.MM.yyyy").format(dateTime)
}

private fun formatTimeOnly(dateTime: LocalDateTime): String {
    return DateTimeFormatter.ofPattern("HH:mm").format(dateTime)
}

fun LocalDateTime?.orNow(): LocalDateTime {
    return this ?: LocalDateTime.now()
}