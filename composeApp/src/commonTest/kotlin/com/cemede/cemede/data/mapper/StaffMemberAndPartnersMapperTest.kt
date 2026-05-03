package com.cemede.cemede.data.mapper

import assertk.assertThat
import assertk.assertions.isEqualTo
import com.cemede.cemede.data.data_base.model.PartnerEntity
import com.cemede.cemede.data.data_base.model.StaffMemberAndPartners
import com.cemede.cemede.data.data_base.model.StaffMemberEntity
import com.cemede.cemede.domain.model.DayOfWeek
import kotlinx.datetime.LocalTime
import kotlin.test.Test

class StaffMemberAndPartnersMapperTest {
    @Test
    fun `mapToStaffMember should map staff member and its partners correctly`() {
        val staffEntity =
            StaffMemberEntity(
                id = 1,
                name = "Dr. Test",
                staffMemberWorkingSchedule = mapOf(DayOfWeek.MONDAY to listOf(LocalTime(8, 0))),
                partnersSchedule = emptyMap(),
            )
        val partnerEntities =
            listOf(
                PartnerEntity(id = 10, name = "Partner A", processType = "Type 1", staffMemberId = 1),
                PartnerEntity(id = 11, name = "Partner B", processType = "Type 2", staffMemberId = 1),
            )
        val staffMemberAndPartners =
            StaffMemberAndPartners(
                staffMember = staffEntity,
                partners = partnerEntities,
            )

        val domain = staffMemberAndPartners.mapToStaffMember()

        assertThat(domain.id).isEqualTo(staffEntity.id)
        assertThat(domain.name).isEqualTo(staffEntity.name)
        assertThat(domain.partners.size).isEqualTo(2)
        assertThat(domain.partners[0].name).isEqualTo("Partner A")
        assertThat(domain.partners[1].name).isEqualTo("Partner B")
        assertThat(domain.staffMemberWorkingSchedule).isEqualTo(staffEntity.staffMemberWorkingSchedule)
    }
}
