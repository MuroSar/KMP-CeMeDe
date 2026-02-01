package com.cemede.cemede.presentation.navigation

import kotlinx.serialization.Serializable

@Serializable
sealed interface NavRoutes {
    @Serializable
    data object Splash : NavRoutes

    @Serializable
    data object Main : NavRoutes

    @Serializable
    data object ProfessorList : NavRoutes

    @Serializable
    data class ProfessorDetail(
        val professorName: String,
    ) : NavRoutes
}
