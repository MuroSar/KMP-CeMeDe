package com.cemede.cemede.presentation.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Groups
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.em
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import cemede.composeapp.generated.resources.Res
import cemede.composeapp.generated.resources.close
import cemede.composeapp.generated.resources.construction_banner_button
import cemede.composeapp.generated.resources.student
import cemede.composeapp.generated.resources.student_dialog_schedule
import cemede.composeapp.generated.resources.students
import com.cemede.cemede.domain.model.Student
import com.cemede.cemede.presentation.theme.ALPHA_0_2
import com.cemede.cemede.presentation.theme.ALPHA_0_7
import com.cemede.cemede.presentation.theme.CemedeTheme
import com.cemede.cemede.presentation.theme.WEIGHT_1
import com.cemede.cemede.presentation.theme.elevation_8
import com.cemede.cemede.presentation.theme.padding_16
import com.cemede.cemede.presentation.theme.padding_24
import com.cemede.cemede.presentation.theme.size_16
import com.cemede.cemede.presentation.theme.size_32
import com.cemede.cemede.presentation.theme.space_12
import kotlinx.datetime.LocalTime
import org.jetbrains.compose.resources.stringResource

object CemedeDialog {
    @Composable
    fun StudentListDialog(
        time: LocalTime,
        students: List<Student>,
        onStudentClicked: (Student) -> Unit,
        onDismiss: () -> Unit,
    ) {
        Dialog(onDismissRequest = onDismiss) {
            Box(modifier = Modifier.wrapContentSize()) {
                Surface(
                    shape = RoundedCornerShape(size_32),
                    shadowElevation = elevation_8,
                ) {
                    Column {
                        // Header
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(padding_16),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically,
                        ) {
                            Column {
                                Text(
                                    text = stringResource(if (students.size == 1) Res.string.student else Res.string.students),
                                    color = MaterialTheme.colorScheme.primary,
                                    fontWeight = FontWeight.Bold,
                                    style = MaterialTheme.typography.titleMedium,
                                )
                                Text(
                                    text = stringResource(Res.string.student_dialog_schedule, time.toString()),
                                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = ALPHA_0_7),
                                    style = MaterialTheme.typography.bodyMedium,
                                )
                            }
                            Box(
                                modifier = Modifier
                                    .background(MaterialTheme.colorScheme.secondary.copy(alpha = ALPHA_0_2), CircleShape)
                                    .padding(8.dp),
                            ) {
                                Icon(
                                    imageVector = if (students.size == 1) Icons.Default.Person else Icons.Default.Groups,
                                    contentDescription = "Alumnos",
                                    tint = MaterialTheme.colorScheme.secondary,
                                )
                            }
                        }

                        // Student List
                        LazyColumn(
                            modifier = Modifier
                                .fillMaxWidth()
                                .weight(WEIGHT_1, fill = false)
                                .padding(padding_16),
                            verticalArrangement = Arrangement.spacedBy(space_12),
                        ) {
                            items(students) { student ->
                                CemedeCard.StudentCard(
                                    student = student,
                                    onCardClick = { student -> onStudentClicked(student) },
                                )
                            }
                        }

                        // Close Button
                        Box(modifier = Modifier.padding(padding_24)) {
                            Button(
                                onClick = onDismiss,
                                modifier = Modifier.fillMaxWidth(),
                                shape = RoundedCornerShape(size_16),
                                colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary),
                            ) {
                                Text(
                                    text = stringResource(Res.string.close).uppercase(),
                                    modifier = Modifier.padding(vertical = 8.dp),
                                    color = Color.White,
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 12.sp,
                                    style = TextStyle(letterSpacing = 0.2.em),
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

@Preview
@Composable
private fun StudentListDialogPreview() {
    val students =
        listOf(
            Student(id = 1, name = "Juan Pérez", processType = "Readaptacion"),
            Student(id = 2, name = "María García", processType = "Deportivo"),
            Student(id = 3, name = "Lucas Rodríguez", processType = "Salud"),
        )
    CemedeTheme {
        CemedeDialog.StudentListDialog(
            time = LocalTime(7, 0, 0),
            students = students,
            onStudentClicked = {},
            onDismiss = {},
        )
    }
}
