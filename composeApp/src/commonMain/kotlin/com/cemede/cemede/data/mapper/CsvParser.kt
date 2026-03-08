package com.cemede.cemede.data.mapper

import com.cemede.cemede.data.data_base.model.StudentEntity
import com.cemede.cemede.domain.model.DayOfWeek
import com.cemede.cemede.domain.util.DateTimeHandler
import kotlinx.datetime.LocalTime

object CsvParser {
    private const val STUDENTS_ROW_STARTS: Int = 1
    private const val STUDENTS_SCHEDULE_ROW_STARTS: Int = 1
    private const val NOT_AN_ANSWER: String = "#N/A"

    fun parseStudents(
        csvData: String,
        professorId: Int,
    ): List<StudentEntity> {
        val lines = csvData.lines()
        val studentNameListIndex = lines.first().split(',').indexOfFirst { it.equals("Listado de alumnos", ignoreCase = true) }
        val processTypeListIndex = lines.first().split(',').indexOfFirst { it.equals("Tipo de proceso", ignoreCase = true) }

        if (studentNameListIndex == -1) {
            return emptyList()
        }

        return lines
            .subList(STUDENTS_ROW_STARTS, lines.size)
            .mapNotNull { line ->
                val studentName = line.split(',').elementAtOrNull(studentNameListIndex)?.trim()
                val processType = line.split(',').elementAtOrNull(processTypeListIndex)?.trim()
                if (studentName.isNullOrBlank() || studentName == NOT_AN_ANSWER) {
                    null
                } else {
                    StudentEntity(
                        name = studentName,
                        processType = processType ?: "",
                        professorId = professorId,
                    )
                }
            }
    }

    /**
     * Parses a CSV string representing a weekly schedule and returns a map.
     * The format of the returned map is: `Map<DayOfWeek, Map<Time, List<String>>>`.
     * E.g., {DayOfWeek.MONDAY, {LocalTime(12,0,0), listOf("Student Name")}}
     *
     * @param csvData The raw CSV string to parse.
     * @return A map representing the schedule with student names.
     */
    fun parseStudentsSchedule(csvData: String): Map<DayOfWeek, Map<LocalTime, List<String>>> {
        val lines = csvData.lines().filter { it.isNotBlank() }
        if (lines.isEmpty()) return emptyMap()

        // 1. Get headers (Days of the week)
        val header = lines.first().split(',')
        val dayHeaders = header.subList(1, 6) // Assuming the columns are [Time, Lunes, Martes, Miercoles, Jueves, Viernes]
        val days = dayHeaders.map { DayOfWeek.fromDisplayName(it.trim()) }

        val studentsSchedule = mutableMapOf<DayOfWeek, MutableMap<LocalTime, List<String>>>()

        // 2. Iterate through schedule rows (skip header)
        lines.drop(STUDENTS_SCHEDULE_ROW_STARTS).forEach { line ->
            val columns = line.split(',')
            val timeLabel = columns.firstOrNull()?.trim() ?: return@forEach
            val time =
                DateTimeHandler.parseTime(
                    timeLabel
                        .replace("a. m.", "AM")
                        .replace("p. m.", "PM"),
                )

            // 3. Iterate through days and count students in each cell
            days.forEachIndexed { index, day ->
                if (day == null) return@forEachIndexed

                // Column index is day index + 1
                val cellContent = columns.getOrNull(index + 1)?.trim() ?: ""

                if (cellContent.isNotBlank() && cellContent != NOT_AN_ANSWER) {
                    val studentNames =
                        cellContent
                            .split("; ")
                            .filter { it.trim().isNotBlank() }
                            .map { it.trim() }

                    if (studentNames.isNotEmpty()) {
                        val timeMap = studentsSchedule.getOrPut(day) { mutableMapOf() }
                        timeMap[time] = studentNames
                    }
                }
            }
        }
        return studentsSchedule
    }
}
