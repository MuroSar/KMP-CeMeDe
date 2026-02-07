package com.cemede.cemede.presentation.screen.professor_list

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import cemede.composeapp.generated.resources.Res
import cemede.composeapp.generated.resources.professor_list_screen_item
import cemede.composeapp.generated.resources.professor_list_screen_search_bar
import cemede.composeapp.generated.resources.professor_list_screen_title
import com.cemede.cemede.domain.model.Professor
import com.cemede.cemede.presentation.theme.padding_16
import com.cemede.cemede.presentation.theme.padding_8
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.koinInject

@Composable
fun ProfessorListScreen(
    onNavigateToProfessorDetail: (Professor) -> Unit,
    viewModel: ProfessorListViewModel = koinInject(),
) {
    val state by viewModel.state.collectAsState()
    var searchQuery by remember { mutableStateOf("") }

    Column(modifier = Modifier.padding(padding_16)) {
        Text(text = stringResource(Res.string.professor_list_screen_title))
        TextField(
            value = searchQuery,
            onValueChange = { searchQuery = it },
            modifier = Modifier.fillMaxWidth(),
            placeholder = { Text(stringResource(Res.string.professor_list_screen_search_bar)) },
        )

        if (state.isLoading) {
            CircularProgressIndicator()
        }

        state.error?.let {
            Text(text = it)
        }

        LazyColumn {
            items(state.professors.filter { it.name.contains(searchQuery, ignoreCase = true) }) {
                Text(
                    text = stringResource(Res.string.professor_list_screen_item, it.id, it.name),
                    modifier =
                        Modifier
                            .fillMaxWidth()
                            .clickable { onNavigateToProfessorDetail(it) }
                            .padding(padding_8),
                )
            }
        }
    }
}
