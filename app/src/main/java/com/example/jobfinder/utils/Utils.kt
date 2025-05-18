package com.example.jobfinder.utils

import com.example.jobfinder.domain.entity.Shift
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.time.format.DateTimeFormatterBuilder
import java.time.temporal.ChronoField

object Utils {

    /* ---------- function ---------- */

    // Chuyển LocalDateTime → String (chỉ định dạng ngày yyyy-MM-dd)
    fun localDateTimeToString(dateTime: LocalDateTime?): String {
        if (dateTime == null) return "null"
        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
        return dateTime.format(formatter)
    }

    // Chuyển String → LocalDateTime với formatter linh hoạt (có hoặc không có phần nano giây)
    fun stringToLocalDateTime(dateTimeString: String?): LocalDateTime? {
        if (dateTimeString.isNullOrBlank() || dateTimeString == "null") {
            return null
        }
        val formatter = DateTimeFormatterBuilder()
            .appendPattern("yyyy-MM-dd'T'HH:mm:ss")
            .optionalStart()
            .appendFraction(ChronoField.NANO_OF_SECOND, 0, 9, true)
            .optionalEnd()
            .toFormatter()
        return LocalDateTime.parse(dateTimeString, formatter)
    }

    // Chuyển string -> Shift (với năm mặc định 2025)
    fun parseShift(input: String, year:Int = 2025): Shift {
        if (input.isNotBlank()){
            // input mẫu: "Morning : 08:00 09-05 -> 16:00 09-05"
            val parts = input.split(" : ")
            val name = parts[0].trim()

            val times = parts[1].split(" -> ")

            val formatter = DateTimeFormatter.ofPattern("HH:mm dd-MM yyyy")

            // Parse startTime và endTime với năm cho trước
            val startLocal = LocalDateTime.parse(
                "${times[0]} $year",
                formatter
            )
            val endLocal = LocalDateTime.parse(
                "${times[1]} $year",
                formatter
            )

            return Shift(
                endTime = endLocal,
                name = name,
                startTime = startLocal
            )
        } else {
            return Shift(
                endTime = LocalDateTime.now(),
                name = "null",
                startTime = LocalDateTime.now()
            )
        }
    }

    // Chuyển Shift -> String
    fun shiftToString(shift: Shift): String { //todo có thể gặp bug khi create Job
        val formatter = DateTimeFormatter.ofPattern("HH:mm dd-MM")
        val startStr = shift.startTime?.format(formatter) ?: "null"
        val endStr = shift.endTime?.format(formatter) ?: "null"
        return "${shift.name} : $startStr -> $endStr"
    }

    //format từ LocalDateTime -> lấy "HH:mm"
    val formatter_Hour_Minus = DateTimeFormatter.ofPattern("HH:mm")
}