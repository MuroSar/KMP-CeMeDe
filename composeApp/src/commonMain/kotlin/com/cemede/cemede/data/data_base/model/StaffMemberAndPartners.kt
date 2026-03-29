package com.cemede.cemede.data.data_base.model

import androidx.room.Embedded
import androidx.room.Relation

data class StaffMemberAndPartners(
    @Embedded val staffMember: StaffMemberEntity,
    @Relation(
        parentColumn = "id",
        entityColumn = "staffMemberId",
    )
    val partners: List<PartnerEntity>,
)
