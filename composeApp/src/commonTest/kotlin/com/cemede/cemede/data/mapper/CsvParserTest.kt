package com.cemede.cemede.data.mapper

import assertk.assertThat
import assertk.assertions.isEmpty
import assertk.assertions.isEqualTo
import assertk.assertions.isNotNull
import com.cemede.cemede.domain.model.DayOfWeek
import kotlinx.datetime.LocalTime
import kotlin.test.Test

class CsvParserTest {
    @Test
    fun `parsePartnersTab should parse valid CSV correctly`() {
        val csvData =
            """
            Nombre y apellido,Fecha de ingreso,Objetivo,Sindrome,Diagnóstico,Profe,Lunes,Martes,Miercoles,Jueves,Viernes,Tipo de horario
            Juan Perez,01/05/2023,Objetivo A,Sindrome X,Diag Y,Tomas,08:00,08:00,,,08:00,Fijo
            """.trimIndent()

        val result = CsvParser.parsePartnersTab(csvData)

        assertThat(result.size).isEqualTo(1)
        val partner = result.first()
        assertThat(partner.name).isEqualTo("Juan Perez")
        assertThat(partner.staffMemberName).isEqualTo("Tomas")
        assertThat(partner.workingSchedule[DayOfWeek.MONDAY]).isEqualTo(LocalTime(8, 0))
        assertThat(partner.workingSchedule[DayOfWeek.FRIDAY]).isEqualTo(LocalTime(8, 0))
    }

    @Test
    fun `parsePartnersTab should skip empty or NA names`() {
        val csvData =
            """
            Nombre y apellido,Fecha de ingreso,Objetivo,Sindrome,Diagnóstico,Profe,Lunes,Martes,Miercoles,Jueves,Viernes,Tipo de horario
            ,01/05/2023,Obj,Sin,Diag,Profe,,,,,,Fijo
            #N/A,01/05/2023,Obj,Sin,Diag,Profe,,,,,,Fijo
            """.trimIndent()

        val result = CsvParser.parsePartnersTab(csvData)

        assertThat(result).isEmpty()
    }

    @Test
    fun `staffMemberSchedule should parse schedule grid correctly`() {
        val csvData =
            """
            Hora,Lunes,Martes,Miercoles,Jueves,Viernes
            08:00,Paciente A,Paciente B,,,Paciente C
            """.trimIndent()

        val result = CsvParser.staffMemberSchedule(csvData)

        assertThat(result[DayOfWeek.MONDAY]?.get(LocalTime(8, 0))).isEqualTo(listOf("Paciente A"))
        assertThat(result[DayOfWeek.TUESDAY]?.get(LocalTime(8, 0))).isEqualTo(listOf("Paciente B"))
        assertThat(result[DayOfWeek.FRIDAY]?.get(LocalTime(8, 0))).isEqualTo(listOf("Paciente C"))
    }

    @Test
    fun `parseStaffMembersWorkingSchedule should parse correctly`() {
        val csvData =
            """
            Hora,Lunes,Martes,Miercoles,Jueves,Viernes
            08:00,Tomas,Macarena,Tomas,Manuel,Melany
            09:00,Tomas,Macarena,Tomas,Manuel,Melany
            """.trimIndent()

        val result = CsvParser.parseStaffMembersWorkingSchedule(csvData)

        assertThat(result["Tomas"]?.get(DayOfWeek.MONDAY)).isEqualTo(listOf(LocalTime(8, 0), LocalTime(9, 0)))
        assertThat(result["Macarena"]?.get(DayOfWeek.TUESDAY)).isEqualTo(listOf(LocalTime(8, 0), LocalTime(9, 0)))
    }

    @Test
    fun `parsePartnersFromStaffMemberTab should parse correctly`() {
        val csvData =
            """
            ,,,,Linea de tiempo 2026,,,,,,,,,,,,,,,,,,,,,,,,,Tomas
Listado de alumnos,Fecha de evaluación inicial,Tipo de proceso,Diagnóstico,Enero,Febrero,Marzo,Abril,Mayo,Junio,Julio,Agosto,Septiembre,Octubre,Noviembre,Diciembre,Alta (si/no),Observaciones,,,,,,,,,,,,
Juan Perez,,,,,,,,,,,,,,,,,,,,,,,,,,,,,
            """.trimIndent()

        val result = CsvParser.parsePartnersFromStaffMemberTab(csvData, staffMemberId = 1)

        assertThat(result.size).isEqualTo(1)
        assertThat(result.first().name).isEqualTo("Juan Perez")
        assertThat(result.first().staffMemberId).isEqualTo(1)
    }
}
