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
import cemede.composeapp.generated.resources.Res
import cemede.composeapp.generated.resources.main_screen_button_protocols
import cemede.composeapp.generated.resources.main_screen_button_search_member
import cemede.composeapp.generated.resources.main_screen_button_search_professor
import cemede.composeapp.generated.resources.main_screen_title
import cemede.composeapp.generated.resources.under_construction
import kotlinx.coroutines.launch
import org.jetbrains.compose.resources.stringResource

@Composable
fun MainScreen(onNavigateToProfessorList: () -> Unit) {
    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()
    val underConstructionMessage = stringResource(Res.string.under_construction)

    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) },
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
        ) {
            Text(stringResource(Res.string.main_screen_title))
            Button(onClick = onNavigateToProfessorList) {
                Text(stringResource(Res.string.main_screen_button_search_professor))
            }
            Button(onClick = {
                scope.launch {
                    snackbarHostState.showSnackbar(underConstructionMessage)
                }
            }) {
                Text(stringResource(Res.string.main_screen_button_search_member))
            }
            Button(onClick = {
                scope.launch {
                    snackbarHostState.showSnackbar(underConstructionMessage)
                }
            }) {
                Text(stringResource(Res.string.main_screen_button_protocols))
            }
        }
    }
}
