package com.cemede.cemede.presentation.navigation

import com.cemede.cemede.domain.model.Partner
import kotlinx.serialization.Serializable

@Serializable
sealed interface NavRoutes {
    @Serializable
    data object Splash : NavRoutes

    @Serializable
    data object Main : NavRoutes

    @Serializable
    data object StaffMemberList : NavRoutes

    @Serializable
    data object PartnerList : NavRoutes

    @Serializable
    data class StaffMemberDetail(
        val staffMemberId: Int,
        val staffMemberName: String,
    ) : NavRoutes

    @Serializable
    data class StaffMemberSchedule(
        val staffMemberId: Int,
        val staffMemberName: String,
        val shouldSyncInfo: Boolean = false,
    ) : NavRoutes

    @Serializable
    data class PartnerDetail(
        val partner: Partner,
    ) : NavRoutes
}
