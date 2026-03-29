package com.cemede.cemede.data.mapper

import com.cemede.cemede.data.data_base.model.PartnerEntity
import com.cemede.cemede.domain.model.DayOfWeek
import com.cemede.cemede.domain.util.DateTimeHandler
import kotlinx.datetime.LocalTime

object CsvParser {
    private const val STAFF_MEMBER_TAB_ROW_STARTS: Int = 1
    private const val PARTNERS_ROW_STARTS: Int = 1
    private const val PARTNERS_SCHEDULE_ROW_STARTS: Int = 1
    private const val NOT_AN_ANSWER: String = "#N/A"

    fun parsePartners(
        csvData: String,
        staffMemberId: Int,
    ): List<PartnerEntity> {
        val lines = csvData.lines().drop(STAFF_MEMBER_TAB_ROW_STARTS)
        val partnerNameListIndex = lines.first().split(',').indexOfFirst { it.equals("Listado de alumnos", ignoreCase = true) }
        val processTypeListIndex = lines.first().split(',').indexOfFirst { it.equals("Tipo de proceso", ignoreCase = true) }

        if (partnerNameListIndex == -1) {
            return emptyList()
        }

        return lines
            .subList(PARTNERS_ROW_STARTS, lines.size)
            .mapNotNull { line ->
                val partnerName = line.split(',').elementAtOrNull(partnerNameListIndex)?.trim()
                val processType = line.split(',').elementAtOrNull(processTypeListIndex)?.trim()
                if (partnerName.isNullOrBlank() || partnerName == NOT_AN_ANSWER) {
                    null
                } else {
                    PartnerEntity(
                        name = partnerName,
                        processType = processType ?: "",
                        staffMemberId = staffMemberId,
                    )
                }
            }
    }

    /**
     * Parses a CSV string representing a weekly schedule and returns a map.
     * The format of the returned map is: `Map<DayOfWeek, Map<Time, List<String>>>`.
     * E.g., {DayOfWeek.MONDAY, {LocalTime(12,0,0), listOf("Partner Name")}}
     *
     * @param csvData The raw CSV string to parse.
     * @return A map representing the schedule with partner names.
     */
    fun parsePartnersSchedule(csvData: String): Map<DayOfWeek, Map<LocalTime, List<String>>> {
        val lines = csvData.lines().filter { it.isNotBlank() }
        if (lines.isEmpty()) return emptyMap()

        // 1. Get headers (Days of the week)
        val header = lines.first().split(',')
        val dayHeaders = header.subList(1, 6) // Assuming the columns are [Time, Lunes, Martes, Miercoles, Jueves, Viernes]
        val days = dayHeaders.map { DayOfWeek.fromDisplayName(it.trim()) }

        val partnersSchedule = mutableMapOf<DayOfWeek, MutableMap<LocalTime, List<String>>>()

        // 2. Iterate through schedule rows (skip header)
        lines.drop(PARTNERS_SCHEDULE_ROW_STARTS).forEach { line ->
            val columns = line.split(',')
            val timeLabel = columns.firstOrNull()?.trim() ?: return@forEach
            val time =
                DateTimeHandler.parseTime(
                    timeLabel
                        .replace("a. m.", "AM")
                        .replace("p. m.", "PM"),
                )

            // 3. Iterate through days and count partners in each cell
            days.forEachIndexed { index, day ->
                if (day == null) return@forEachIndexed

                // Column index is day index + 1
                val cellContent = columns.getOrNull(index + 1)?.trim() ?: ""

                if (cellContent.isNotBlank() && cellContent != NOT_AN_ANSWER) {
                    val partnerNames =
                        cellContent
                            .split("; ")
                            .filter { it.trim().isNotBlank() }
                            .map { it.trim() }

                    if (partnerNames.isNotEmpty()) {
                        val timeMap = partnersSchedule.getOrPut(day) { mutableMapOf() }
                        timeMap[time] = partnerNames
                    }
                }
            }
        }
        return partnersSchedule
    }

    /**
     * Parses a CSV string representing the staff members' working schedule.
     * Returns a map of Staff Member Name to their working schedule (Day -> List of Times).
     */
    fun parseStaffMembersWorkingSchedule(csvData: String): Map<String, Map<DayOfWeek, List<LocalTime>>> {
        val lines = csvData.lines().filter { it.isNotBlank() }
        if (lines.isEmpty()) return emptyMap()

        val header = lines.first().split(',')
        val dayHeaders = header.subList(1, 6)
        val days = dayHeaders.map { DayOfWeek.fromDisplayName(it.trim()) }

        val staffMemberWorkingSchedules = mutableMapOf<String, MutableMap<DayOfWeek, MutableList<LocalTime>>>()

        lines.drop(1).forEach { line ->
            val columns = line.split(',')
            val timeLabel = columns.firstOrNull()?.trim() ?: return@forEach
            val time =
                DateTimeHandler.parseTime(
                    timeLabel
                        .replace("a. m.", "AM")
                        .replace("p. m.", "PM"),
                )

            days.forEachIndexed { index, day ->
                if (day == null) return@forEachIndexed
                val cellContent = columns.getOrNull(index + 1)?.trim() ?: ""

                if (cellContent.isNotBlank() && cellContent != NOT_AN_ANSWER) {
                    val staffMemberNames =
                        cellContent
                            .split("\n", ";")
                            .map { it.trim() }
                            .filter { it.isNotBlank() }

                    staffMemberNames.forEach { name ->
                        val schedule = staffMemberWorkingSchedules.getOrPut(name) { mutableMapOf() }
                        val times = schedule.getOrPut(day) { mutableListOf() }
                        times.add(time)
                    }
                }
            }
        }
        return staffMemberWorkingSchedules
    }
}
