package com.cemede.cemede.data.mapper

import com.cemede.cemede.data.data_base.model.StudentEntity

object CsvParser {
    fun parseStudents(
        csvData: String,
        professorId: Int,
    ): List<StudentEntity> {
        val lines = csvData.lines()
        val studentListStartIndex = lines.indexOf("Listado de alumnos") + 1

        if (studentListStartIndex == 0) { // indexOf returns -1 if not found, so +1 makes it 0
            return emptyList()
        }

        return lines
            .subList(studentListStartIndex, lines.size)
            .mapNotNull { line ->
                val studentName = line.split(',').firstOrNull()?.trim()
                if (studentName.isNullOrBlank()) {
                    null
                } else {
                    StudentEntity(name = studentName, professorId = professorId)
                }
            }
    }
}
