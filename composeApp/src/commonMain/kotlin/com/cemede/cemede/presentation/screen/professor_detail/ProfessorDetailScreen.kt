package com.cemede.cemede.presentation.screen.professor_detail

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.FilterList
import androidx.compose.material.icons.filled.Groups
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import cemede.composeapp.generated.resources.Res
import cemede.composeapp.generated.resources.back
import cemede.composeapp.generated.resources.clear_search
import cemede.composeapp.generated.resources.empty_state_subtitle
import cemede.composeapp.generated.resources.filter
import cemede.composeapp.generated.resources.professor_detail_screen_empty_state_title
import cemede.composeapp.generated.resources.professor_detail_screen_header_title
import cemede.composeapp.generated.resources.professor_detail_screen_loading
import cemede.composeapp.generated.resources.professor_detail_screen_search_bar
import cemede.composeapp.generated.resources.professor_detail_screen_student_list
import cemede.composeapp.generated.resources.synchronizing_data
import com.cemede.cemede.domain.model.Professor
import com.cemede.cemede.domain.model.Student
import com.cemede.cemede.presentation.component.CemedeBanner
import com.cemede.cemede.presentation.component.CemedeCard
import com.cemede.cemede.presentation.component.CemedeEmptyState
import com.cemede.cemede.presentation.component.CemedeLoader
import com.cemede.cemede.presentation.component.CemedeSearchBar
import com.cemede.cemede.presentation.component.CemedeTopAppBar
import com.cemede.cemede.presentation.theme.ALPHA_0_2
import com.cemede.cemede.presentation.theme.CemedeTheme
import com.cemede.cemede.presentation.theme.height_16
import com.cemede.cemede.presentation.theme.height_8
import com.cemede.cemede.presentation.theme.padding_16
import com.cemede.cemede.presentation.theme.padding_8
import com.cemede.cemede.presentation.theme.size_16
import com.cemede.cemede.presentation.theme.width_4
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.koinInject
import org.koin.core.parameter.parametersOf

@Composable
fun ProfessorDetailScreen(
    professor: Professor,
    onNavigateBack: () -> Unit,
    onNavigateToStudentDetail: (Student) -> Unit,
    viewModel: ProfessorDetailViewModel = koinInject(parameters = { parametersOf(professor) }),
) {
    val state by viewModel.state.collectAsState()

    ProfessorDetailContent(
        state = state,
        onNavigateBack = onNavigateBack,
        onNavigateToStudentDetail = onNavigateToStudentDetail,
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfessorDetailContent(
    state: ProfessorDetailState,
    onNavigateBack: () -> Unit,
    onNavigateToStudentDetail: (Student) -> Unit,
) {
    var showConstructionBanner by remember { mutableStateOf(false) }

    CemedeTheme {
        Scaffold(
            topBar = {
                CemedeTopAppBar.TopAppBar(
                    title = stringResource(Res.string.professor_detail_screen_header_title),
                    navigationIcon = {
                        IconButton(onClick = onNavigateBack) {
                            Icon(
                                imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                                contentDescription = stringResource(Res.string.back),
                            )
                        }
                    },
                )
            },
        ) { paddingValues ->
            Box(modifier = Modifier.fillMaxSize()) {
                if (state.isLoading) {
                    CemedeLoader(
                        title = stringResource(Res.string.synchronizing_data),
                        subtitle = stringResource(Res.string.professor_detail_screen_loading),
                    )
                } else if (state.professor != null) {
                    Column(modifier = Modifier.padding(paddingValues)) {
                        CemedeCard.ProfessorDetailCard(
                            modifier = Modifier.fillMaxWidth(),
                            professor = state.professor,
                            onCallButtonClick = { showConstructionBanner = true },
                            onMessageButtonClick = { showConstructionBanner = true },
                        )
                        //TODO: Llegué hasta acá
                        WeeklySchedule(
                            studentsSchedule = state.professor.studentsSchedule,
                        )
                        AssignedStudents(
                            students = state.professor.students,
                            onNavigateToStudentDetail = { showConstructionBanner = true },
                        )
                    }
                } else {
                    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        Text(state.error ?: "No se encontró el profesor.")
                    }
                }

                CemedeBanner.ConstructionBanner(
                    modifier =
                        Modifier
                            .align(Alignment.BottomCenter)
                            .padding(bottom = padding_16, start = padding_16, end = padding_16),
                    showBanner = showConstructionBanner,
                    onDismiss = { showConstructionBanner = false },
                )
            }
        }
    }
}

data class Schedule(val time: String, val studentCount: Int, val isIndividual: Boolean, val isActive: Boolean)

val weeklyScheduleItems = listOf(
    Schedule("07:00 AM", 4, false, false),
    Schedule("09:00 AM", 2, false, true),
    Schedule("11:30 AM", 1, true, false),
    Schedule("01:00 PM", 6, false, false)
)

@Composable
private fun WeeklySchedule(
    studentsSchedule: Map<String, Map<String, Int>>,
) {
    Column(
        modifier = Modifier
            .background(Color.White.copy(alpha = ALPHA_0_2))
            .padding(vertical = 24.dp)

    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                "Cronograma Semanal",
                style = MaterialTheme.typography.labelMedium,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.primary
            )
            Text(
                text = "JUNIO 2024",
                style = MaterialTheme.typography.labelSmall,
                color = MaterialTheme.colorScheme.secondary,
            )
        }
        Spacer(modifier = Modifier.height(16.dp))
        LazyRow(
            contentPadding = PaddingValues(horizontal = 16.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp),
        ) {
            items(weeklyScheduleItems) { schedule ->
                WeeklyScheduleItem(schedule)
            }
        }
    }
}

@Composable
private fun WeeklyScheduleItem(schedule: Schedule) {
    val backgroundColor = if (schedule.isActive) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.surface
    val contentColor = if (schedule.isActive) MaterialTheme.colorScheme.onPrimary else MaterialTheme.colorScheme.onSurface
    val subContentColor = if (schedule.isActive) Color.White.copy(alpha = 0.7f) else MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f)

    Card(
        modifier = Modifier.width(110.dp),
        colors = CardDefaults.cardColors(containerColor = backgroundColor),
        elevation = CardDefaults.cardElevation(if (schedule.isActive) 4.dp else 1.dp)
    ) {
        Column(modifier = Modifier.padding(12.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
            Text(schedule.time, style = MaterialTheme.typography.labelSmall, color = subContentColor)
            Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                Icon(
                    if (schedule.isIndividual) Icons.Default.Person else Icons.Default.Groups,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.secondary,
                    modifier = Modifier.size(16.dp)
                )
                Text(
                    if (schedule.isIndividual) "Individual" else "${schedule.studentCount} Alumnos",
                    style = MaterialTheme.typography.labelMedium,
                    fontWeight = FontWeight.Bold,
                    color = contentColor
                )
            }
            LinearProgressIndicator(
                progress = if (schedule.isIndividual) 0f else schedule.studentCount / 6f,
                modifier = Modifier.fillMaxWidth(),
                color = MaterialTheme.colorScheme.secondary,
                trackColor = contentColor.copy(alpha = 0.2f)
            )
        }
    }
}

@Composable
private fun AssignedStudents(
    students: List<Student>,
    onNavigateToStudentDetail: (Student) -> Unit,
) {
    var searchQuery by remember { mutableStateOf("") }
    var showSearchBar by remember { mutableStateOf(false) }

    Column(modifier = Modifier.padding(horizontal = padding_16)) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = stringResource(Res.string.professor_detail_screen_student_list),
                style = MaterialTheme.typography.labelMedium,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.primary
            )
            TextButton(
                onClick = { showSearchBar = !showSearchBar },
                enabled = students.isNotEmpty()
            ) {
                Icon(
                    imageVector = Icons.Default.FilterList,
                    contentDescription = "Ícono de filtrar",
                    modifier = Modifier.size(size_16)
                )
                Spacer(modifier = Modifier.width(width_4))
                Text(
                    text = stringResource(Res.string.filter),
                    style = MaterialTheme.typography.labelSmall,
                )
            }
        }
        Spacer(modifier = Modifier.height(height_8))

        if (showSearchBar) {
            CemedeSearchBar.SearchBar(
                modifier = Modifier.padding(vertical = padding_8),
                placeholder = stringResource(Res.string.professor_detail_screen_search_bar),
                searchQuery = searchQuery,
                onSearchQueryChange = { searchQuery = it },
            )
        }

        if (students.isEmpty()) {
            CemedeEmptyState.EmptyState(
                title = stringResource(Res.string.professor_detail_screen_empty_state_title),
            )
        } else {
            val filteredStudents =
                students.filter { student ->
                    student.name.contains(searchQuery, ignoreCase = true)
                }

            if (filteredStudents.isEmpty() && searchQuery.isNotEmpty()) {
                CemedeEmptyState.EmptyState(
                    title = stringResource(Res.string.professor_detail_screen_empty_state_title),
                    subtitle = stringResource(Res.string.empty_state_subtitle),
                    actionText = stringResource(Res.string.clear_search),
                    onActionClick = { searchQuery = "" },
                )
            } else {
                LazyColumn {
                    items(filteredStudents) { student ->
                        CemedeCard.StudentCard(
                            student = student,
                            onCardClick = { onNavigateToStudentDetail(student) },
                        )
                        Spacer(modifier = Modifier.height(height_16))
                    }
                }
            }
        }
    }
}

@Preview(showSystemUi = true)
@Composable
private fun ProfessorDetailScreenPreview() {
    val students = listOf(
        Student(id = 1, name = "Juan Pérez", processType = "Readaptacion"),
        Student(id = 2, name = "María García", processType = "Deportivo"),
        Student(id = 3, name = "Lucas Rodríguez", processType = "Salud"),
    )
    val professor = Professor(1, "Prof. Macarena", true, students = students)
    ProfessorDetailContent(
        state = ProfessorDetailState(professor = professor, isLoading = false),
        onNavigateBack = {},
        onNavigateToStudentDetail = {},
    )
}

@Preview(showSystemUi = true, name = "Loading Preview")
@Composable
private fun ProfessorDetailScreenLoadingPreview() {
    ProfessorDetailContent(
        state = ProfessorDetailState(isLoading = true),
        onNavigateBack = {},
        onNavigateToStudentDetail = {},
    )
}

@Preview(showSystemUi = true, name = "No students Preview")
@Composable
private fun ProfessorDetailScreenNoStudentsPreview() {
    val professor = Professor(1, "Prof. Macarena", true, students = emptyList())

    ProfessorDetailContent(
        state = ProfessorDetailState(professor = professor, isLoading = false),
        onNavigateBack = {},
        onNavigateToStudentDetail = {},
    )
}
