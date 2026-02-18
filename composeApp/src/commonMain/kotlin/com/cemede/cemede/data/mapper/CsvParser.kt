package com.cemede.cemede.data.mapper

import com.cemede.cemede.data.data_base.model.StudentEntity

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
     * The format of the returned map is: `Map<Day, Map<Time, StudentCount>>`.
     * E.g., {"Lunes", {"12:00:00 p. m.", 6}}
     *
     * @param csvData The raw CSV string to parse.
     * @return A map representing the schedule with student counts.
     */
    fun parseStudentsSchedule(csvData: String): Map<String, Map<String, Int>> {
        val lines = csvData.lines().filter { it.isNotBlank() }
        if (lines.isEmpty()) return emptyMap()

        // 1. Get headers (Days of the week)
        val header = lines.first().split(',')
        val days = header.subList(1, 6) // Assuming the columns are [Time, Lunes, Martes, Miercoles, Jueves, Viernes]

        val studentsSchedule = mutableMapOf<String, MutableMap<String, Int>>()

        // 2. Iterate through schedule rows (skip header)
        lines.drop(STUDENTS_SCHEDULE_ROW_STARTS).forEach { line ->
            val columns = line.split(',')
            val timeLabel = columns.firstOrNull()?.trim() ?: return@forEach

            // 3. Iterate through days and count students in each cell
            days.forEachIndexed { index, day ->
                // Column index is day index + 1
                val cellContent = columns.getOrNull(index + 1)?.trim() ?: ""

                if (cellContent.isNotBlank() && cellContent != NOT_AN_ANSWER) {
                    val studentCount = cellContent.split("; ")
                        .filter { it.trim().isNotBlank() }
                        .size

                    if (studentCount > 0) {
                        val timeMap = studentsSchedule.getOrPut(day.trim()) { mutableMapOf() }
                        timeMap[timeLabel] = studentCount
                    }
                }
            }
        }
        return studentsSchedule
    }
}
