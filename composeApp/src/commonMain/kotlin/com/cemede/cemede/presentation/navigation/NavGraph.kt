package com.cemede.cemede.presentation.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.cemede.cemede.domain.model.Professor
import com.cemede.cemede.presentation.screen.main.MainScreen
import com.cemede.cemede.presentation.screen.professor_detail.ProfessorDetailScreen
import com.cemede.cemede.presentation.screen.professor_list.ProfessorListScreen
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
        MainScreen(onNavigateToProfessorList = { navController.navigate(NavRoutes.ProfessorList) })
    }
    composable<NavRoutes.ProfessorList> {
        ProfessorListScreen(
            onNavigateBack = { navController.popBackStack() },
            onNavigateToProfessorDetail = { professor ->
                navController.navigate(
                    NavRoutes.ProfessorDetail(
                        professorId = professor.id,
                        professorName = professor.name,
                        professorIsWorking = professor.isWorking,
                    ),
                )
            },
        )
    }
    composable<NavRoutes.ProfessorDetail> { backStackEntry ->
        val professorDetail: NavRoutes.ProfessorDetail = backStackEntry.toRoute()
        ProfessorDetailScreen(
            professor =
                Professor(
                    id = professorDetail.professorId,
                    name = professorDetail.professorName,
                    isWorking = professorDetail.professorIsWorking,
                ),
            onBack = { navController.popBackStack() },
        )
    }
}
