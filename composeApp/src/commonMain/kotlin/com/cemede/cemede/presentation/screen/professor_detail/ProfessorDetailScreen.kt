package com.cemede.cemede.presentation.screen.professor_detail

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import org.koin.compose.koinInject

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfessorDetailScreen(
    professorName: String,
    onBack: () -> Unit,
    viewModel: ProfessorDetailViewModel = koinInject(),
) {
    val state by viewModel.state.collectAsState()
    var searchQuery by remember { mutableStateOf("") }

    LaunchedEffect(professorName) {
        viewModel.getProfessor(professorName)
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(professorName) },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                },
            )
        },
    ) {
        Column(modifier = Modifier.padding(it).padding(16.dp)) {
            state.professor?.let { professor ->
                TextField(
                    value = searchQuery,
                    onValueChange = { searchQuery = it },
                    modifier = Modifier.fillMaxWidth(),
                    placeholder = { Text("Buscar alumno") },
                )

                LazyColumn {
                    items(professor.students.filter { student -> student.name.contains(searchQuery, ignoreCase = true) }) {
                        Text(text = it.name, modifier = Modifier.padding(8.dp))
                    }
                }
            }

            if (state.isLoading) {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator()
                }
            }

            state.error?.let { error ->
                if (state.professor == null) {
                    // Empty state
                    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        Text(text = "No hay datos disponibles. Conéctate a internet para descargar los datos.")
                    }
                } else {
                    // Warning banner
                    Box(
                        modifier = Modifier.fillMaxWidth().background(Color.Red).padding(8.dp),
                        contentAlignment = Alignment.Center,
                    ) {
                        Text(text = "Esta información puede no estar actualizada", color = Color.White)
                    }
                }

                // Error dialog
                AlertDialog(
                    onDismissRequest = { /* TODO */ },
                    title = { Text("Error") },
                    text = { Text("Ha ocurrido un error inesperado. Por favor, inténtalo más tarde.") },
                    confirmButton = {
                        Button(onClick = onBack) {
                            Text("Aceptar")
                        }
                    },
                )
            }
        }
    }
}
