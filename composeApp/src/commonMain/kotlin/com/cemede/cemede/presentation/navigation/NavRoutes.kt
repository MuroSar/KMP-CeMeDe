package com.cemede.cemede.presentation.navigation

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
}
