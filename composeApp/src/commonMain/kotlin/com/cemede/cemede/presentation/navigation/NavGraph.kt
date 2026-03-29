package com.cemede.cemede.presentation.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.cemede.cemede.domain.model.StaffMember
import com.cemede.cemede.presentation.screen.main.MainScreen
import com.cemede.cemede.presentation.screen.partner_list.PartnerListScreen
import com.cemede.cemede.presentation.screen.staff_member_detail.StaffMemberDetailScreen
import com.cemede.cemede.presentation.screen.staff_member_list.StaffMemberListScreen
import com.cemede.cemede.presentation.screen.splash.SplashScreen

fun NavGraphBuilder.addCeMeDeScreenGraph(navController: NavController) {
    composable<NavRoutes.Splash> {
        SplashScreen(
            onSplashFinished = {
                navController.navigate(NavRoutes.Main) {
                    popUpTo(NavRoutes.Splash) {
                        // Indicates how far up the backstack should be cleared
                        inclusive = true // Includes the SplashScreen in the backstack removal
                    }
                    launchSingleTop = true // Prevents multiple instances of the MainScreen if it's already on top.
                }
            },
        )
    }
    composable<NavRoutes.Main> {
        MainScreen(
            onNavigateToStaffMemberList = { navController.navigate(NavRoutes.StaffMemberList) },
            onNavigateToPartnerList = { navController.navigate(NavRoutes.PartnerList) }
        )
    }
    composable<NavRoutes.StaffMemberList> {
        StaffMemberListScreen(
            onNavigateBack = { navController.popBackStack() },
            onNavigateToStaffMemberDetail = { staffMember ->
                navController.navigate(
                    NavRoutes.StaffMemberDetail(
                        staffMemberId = staffMember.id,
                        staffMemberName = staffMember.name,
                    ),
                )
            },
        )
    }
    composable<NavRoutes.StaffMemberDetail> { backStackEntry ->
        val staffMemberDetail: NavRoutes.StaffMemberDetail = backStackEntry.toRoute()
        StaffMemberDetailScreen(
            staffMember =
                StaffMember(
                    id = staffMemberDetail.staffMemberId,
                    name = staffMemberDetail.staffMemberName,
                ),
            onNavigateBack = { navController.popBackStack() },
            onNavigateToPartnerDetail = { },
        )
    }
    composable<NavRoutes.PartnerList> {
        PartnerListScreen(
            onNavigateToPartnerDetail = { partner -> },
            onNavigateBack = { navController.popBackStack() }
        )
    }
}
