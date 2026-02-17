package com.cemede.cemede.data.mapper

import com.cemede.cemede.data.data_base.model.StudentEntity

object CsvParser {
    private const val STUDENTS_ROW_STARTS: Int = 1
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
}
