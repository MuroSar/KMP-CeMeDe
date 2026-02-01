package com.cemede.cemede.presentation.screen.main

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import kotlinx.coroutines.launch

@Composable
fun MainScreen(onNavigateToProfessorList: () -> Unit) {
    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()

    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) },
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
        ) {
            Button(onClick = onNavigateToProfessorList) {
                Text("Buscar profe")
            }
            Button(onClick = {
                scope.launch {
                    snackbarHostState.showSnackbar("En construcción")
                }
            }) {
                Text("Buscar alumno")
            }
            Button(onClick = {
                scope.launch {
                    snackbarHostState.showSnackbar("En construcción")
                }
            }) {
                Text("Readaptación")
            }
        }
    }
}
