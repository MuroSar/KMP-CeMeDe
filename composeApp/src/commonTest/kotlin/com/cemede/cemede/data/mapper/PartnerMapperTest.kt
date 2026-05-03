package com.cemede.cemede.data.mapper

import assertk.assertThat
import assertk.assertions.isEqualTo
import com.cemede.cemede.data.data_base.model.PartnerEntity
import com.cemede.cemede.domain.model.DayOfWeek
import com.cemede.cemede.domain.model.ScheduleType
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalTime
import kotlin.test.Test

class PartnerMapperTest {

    @Test
    fun `mapToPartner should map all fields correctly`() {
        val entity = PartnerEntity(
            id = 1,
            name = "Test Partner",
            entryDate = LocalDate(2023, 1, 1),
            processType = "Type A",
            syndrome = "Syndrome X",
            diagnosis = "Diagnosis Y",
            staffMemberName = "Staff A",
            scheduleType = ScheduleType.FIXED,
            workingSchedule = mapOf(DayOfWeek.MONDAY to LocalTime(10, 0))
        )

        val domain = entity.mapToPartner()

        assertThat(domain.id).isEqualTo(entity.id)
        assertThat(domain.name).isEqualTo(entity.name)
        assertThat(domain.entryDate).isEqualTo(entity.entryDate)
        assertThat(domain.processType).isEqualTo(entity.processType)
        assertThat(domain.syndrome).isEqualTo(entity.syndrome)
        assertThat(domain.diagnosis).isEqualTo(entity.diagnosis)
        assertThat(domain.staffMemberName).isEqualTo(entity.staffMemberName)
        assertThat(domain.scheduleType).isEqualTo(entity.scheduleType)
        assertThat(domain.workingSchedule).isEqualTo(entity.workingSchedule)
    }
}
