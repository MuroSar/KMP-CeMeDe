package com.cemede.cemede

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.cemede.cemede.presentation.navigation.NavRoutes
import com.cemede.cemede.presentation.navigation.addCeMeDeScreenGraph

@Composable
@Preview
fun App() {
    MaterialTheme {
        CeMeDeApp()
    }
}

@Composable
fun CeMeDeApp() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = NavRoutes.Splash) {
        addCeMeDeScreenGraph(navController)
    }
}
