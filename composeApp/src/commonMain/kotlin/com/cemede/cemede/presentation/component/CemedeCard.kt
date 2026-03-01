package com.cemede.cemede.presentation.component

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.filled.ChatBubble
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material.icons.filled.Groups
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import cemede.composeapp.generated.resources.Res
import cemede.composeapp.generated.resources.cemede_logo
import cemede.composeapp.generated.resources.professor_card_action_call
import cemede.composeapp.generated.resources.professor_card_action_message
import cemede.composeapp.generated.resources.professor_card_action_schedule
import cemede.composeapp.generated.resources.professor_card_id
import cemede.composeapp.generated.resources.professor_card_name
import cemede.composeapp.generated.resources.professor_card_photo_content_description
import cemede.composeapp.generated.resources.professor_detail_screen_daily_schedule_charged_capacity
import cemede.composeapp.generated.resources.professor_detail_screen_daily_schedule_normal_capacity
import cemede.composeapp.generated.resources.professor_detail_screen_daily_schedule_overloaded_capacity
import cemede.composeapp.generated.resources.professor_detail_screen_individual_student
import cemede.composeapp.generated.resources.professor_detail_screen_multiple_students
import com.cemede.cemede.domain.model.Professor
import com.cemede.cemede.domain.model.Student
import com.cemede.cemede.domain.util.DateTimeHandler
import com.cemede.cemede.presentation.theme.ALPHA_0_2
import com.cemede.cemede.presentation.theme.ALPHA_0_5
import com.cemede.cemede.presentation.theme.ALPHA_0_7
import com.cemede.cemede.presentation.theme.CemedeTheme
import com.cemede.cemede.presentation.theme.GreenNormalCapacity
import com.cemede.cemede.presentation.theme.RedOverloadedCapacity
import com.cemede.cemede.presentation.theme.WEIGHT_1
import com.cemede.cemede.presentation.theme.YellowChargedCapacity
import com.cemede.cemede.presentation.theme.elevation_1
import com.cemede.cemede.presentation.theme.elevation_2
import com.cemede.cemede.presentation.theme.elevation_4
import com.cemede.cemede.presentation.theme.height_48
import com.cemede.cemede.presentation.theme.padding_10
import com.cemede.cemede.presentation.theme.padding_12
import com.cemede.cemede.presentation.theme.padding_16
import com.cemede.cemede.presentation.theme.padding_2
import com.cemede.cemede.presentation.theme.padding_8
import com.cemede.cemede.presentation.theme.size_14
import com.cemede.cemede.presentation.theme.size_16
import com.cemede.cemede.presentation.theme.size_18
import com.cemede.cemede.presentation.theme.size_20
import com.cemede.cemede.presentation.theme.size_44
import com.cemede.cemede.presentation.theme.size_56
import com.cemede.cemede.presentation.theme.space_12
import com.cemede.cemede.presentation.theme.space_16
import com.cemede.cemede.presentation.theme.space_4
import com.cemede.cemede.presentation.theme.space_8
import com.cemede.cemede.presentation.theme.width_110
import com.cemede.cemede.presentation.theme.width_2
import com.cemede.cemede.presentation.theme.width_4
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.LocalTime
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource

object CemedeCard {
    @Composable
    fun ProfessorCard(
        modifier: Modifier = Modifier,
        professor: Professor,
        onCardClick: () -> Unit,
        onCallButtonClick: () -> Unit,
        onMessageButtonClick: () -> Unit,
        onScheduleButtonClick: () -> Unit,
    ) {
        Card(
            onClick = onCardClick,
            shape = RoundedCornerShape(size_16),
            elevation = CardDefaults.cardElevation(elevation_2),
            modifier = modifier.fillMaxWidth(),
        ) {
            Column {
                Row(
                    modifier = Modifier.padding(padding_16),
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Box {
                        Image(
                            painter = painterResource(Res.drawable.cemede_logo),
                            contentDescription = stringResource(Res.string.professor_card_photo_content_description, professor.name),
                            modifier =
                                Modifier
                                    .size(size_56)
                                    .clip(CircleShape)
                                    .border(
                                        width = width_2,
                                        color = MaterialTheme.colorScheme.secondary,
                                        shape = CircleShape,
                                    ),
                        )
                        if (professor.isWorking) {
                            Box(
                                modifier =
                                    Modifier
                                        .size(size_16)
                                        .background(Color.Green, CircleShape)
                                        .border(
                                            width = width_2,
                                            color = MaterialTheme.colorScheme.surface,
                                            shape = CircleShape,
                                        ).align(Alignment.BottomEnd),
                            )
                        }
                    }
                    Spacer(modifier = Modifier.padding(horizontal = padding_8))
                    Column {
                        Text(
                            text = stringResource(Res.string.professor_card_name, professor.name),
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold,
                        )
                        Text(
                            text = stringResource(Res.string.professor_card_id, professor.id),
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onSurface.copy(alpha = ALPHA_0_7),
                        )
                    }
                }
                HorizontalDivider()
                Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceEvenly) {
                    TextButton(onClick = onCallButtonClick) {
                        Icon(
                            modifier = Modifier.size(size_18),
                            imageVector = Icons.Default.Call,
                            contentDescription = "Ícono de llamada",
                        )
                        Spacer(modifier = Modifier.padding(horizontal = padding_2))
                        Text(
                            text = stringResource(Res.string.professor_card_action_call),
                            style = MaterialTheme.typography.bodySmall,
                        )
                    }
                    VerticalDivider(
                        modifier =
                            Modifier
                                .height(height_48)
                                .padding(vertical = padding_8),
                    )
                    TextButton(onClick = onMessageButtonClick) {
                        Icon(
                            modifier = Modifier.size(size_18),
                            imageVector = Icons.Default.ChatBubble,
                            contentDescription = "Ícono de mensaje",
                        )
                        Spacer(modifier = Modifier.padding(horizontal = padding_2))
                        Text(
                            text = stringResource(Res.string.professor_card_action_message),
                            style = MaterialTheme.typography.bodySmall,
                        )
                    }
                    VerticalDivider(
                        modifier =
                            Modifier
                                .height(height_48)
                                .padding(vertical = padding_8),
                    )
                    TextButton(onClick = onScheduleButtonClick) {
                        Icon(
                            modifier = Modifier.size(size_18),
                            imageVector = Icons.Default.CalendarMonth,
                            contentDescription = "Ícono de horarios",
                        )
                        Spacer(modifier = Modifier.padding(horizontal = padding_2))
                        Text(
                            text = stringResource(Res.string.professor_card_action_schedule),
                            style = MaterialTheme.typography.bodySmall,
                        )
                    }
                }
            }
        }
    }

    @Composable
    fun ProfessorDetailCard(
        modifier: Modifier = Modifier,
        professor: Professor,
        onCallButtonClick: () -> Unit,
        onMessageButtonClick: () -> Unit,
    ) {
        Card(
            shape = RoundedCornerShape(0.dp),
            elevation = CardDefaults.cardElevation(elevation_2),
            modifier = modifier.fillMaxWidth(),
        ) {
            Column(
                modifier =
                    Modifier
                        .fillMaxWidth()
                        .padding(padding_16),
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(space_16),
                ) {
                    Box {
                        Image(
                            painter = painterResource(Res.drawable.cemede_logo), // Placeholder
                            contentDescription = "Profile picture of ${professor.name}",
                            modifier =
                                Modifier
                                    .size(80.dp)
                                    .clip(CircleShape)
                                    .border(2.dp, MaterialTheme.colorScheme.secondary, CircleShape)
                                    .padding(2.dp)
                                    .clip(CircleShape),
                            contentScale = ContentScale.Crop,
                        )
                        if (professor.isWorking) {
                            Box(
                                modifier =
                                    Modifier
                                        .size(size_20)
                                        .background(Color.Green, CircleShape)
                                        .border(
                                            width = width_2,
                                            color = MaterialTheme.colorScheme.surface,
                                            shape = CircleShape,
                                        ).align(Alignment.BottomEnd),
                            )
                        }
                    }

                    Column(modifier = Modifier.weight(WEIGHT_1)) {
                        Text(
                            text = stringResource(Res.string.professor_card_name, professor.name),
                            style = MaterialTheme.typography.headlineSmall,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.primary,
                        )
                        Text(
                            modifier = Modifier.padding(bottom = padding_12),
                            text = stringResource(Res.string.professor_card_id, professor.id),
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onSurface.copy(alpha = ALPHA_0_7),
                        )

                        Row(horizontalArrangement = Arrangement.spacedBy(space_8)) {
                            Button(
                                onClick = onMessageButtonClick,
                                modifier = Modifier.weight(WEIGHT_1),
                                contentPadding = PaddingValues(vertical = padding_10),
                            ) {
                                Icon(
                                    imageVector = Icons.Default.ChatBubble,
                                    contentDescription = "Ícono de mensaje",
                                    modifier = Modifier.size(size_14),
                                )
                                Spacer(Modifier.width(width_4))
                                Text(
                                    text = stringResource(Res.string.professor_card_action_message).uppercase(),
                                    style = MaterialTheme.typography.labelSmall,
                                )
                            }
                            OutlinedButton(
                                onClick = onCallButtonClick,
                                modifier = Modifier.weight(WEIGHT_1),
                                contentPadding = PaddingValues(vertical = padding_10),
                                border =
                                    BorderStroke(
                                        width = width_2,
                                        color = MaterialTheme.colorScheme.secondary,
                                    ),
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Call,
                                    contentDescription = "Ícono de llamada",
                                    modifier = Modifier.size(size_14),
                                )
                                Spacer(Modifier.width(width_4))
                                Text(
                                    text = stringResource(Res.string.professor_card_action_call).uppercase(),
                                    style = MaterialTheme.typography.labelSmall,
                                )
                            }
                        }
                    }
                }
            }
        }
    }

    @Composable
    fun StudentCard(
        modifier: Modifier = Modifier,
        student: Student,
        onCardClick: (Student) -> Unit,
    ) {
        Card(
            modifier = modifier.fillMaxWidth(),
            onClick = { onCardClick(student) },
        ) {
            Row(
                modifier = Modifier.padding(padding_12),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(space_12),
            ) {
                Image(
                    painter = painterResource(Res.drawable.cemede_logo),
                    contentDescription = "Foto de perfil de ${student.name}",
                    modifier = Modifier.size(size_44).clip(CircleShape),
                )
                Column(modifier = Modifier.weight(WEIGHT_1)) {
                    Text(
                        text = student.name,
                        style = MaterialTheme.typography.bodyMedium,
                        fontWeight = FontWeight.Bold,
                    )
                    Text(
                        text = student.processType,
                        style = MaterialTheme.typography.labelSmall,
                        color = MaterialTheme.colorScheme.secondary,
                    ) // Mock data
                }
                Icon(
                    imageVector = Icons.Default.ChevronRight,
                    contentDescription = null,
                )
            }
        }
    }

    @Composable
    fun WeeklyScheduleCard(
        time: LocalTime,
        students: List<Student>,
        now: LocalDateTime,
        onCardClick: (students: List<Student>) -> Unit,
    ) {
        val currentHour = now.hour
        val isActive = time.hour == currentHour

        val backgroundColor = if (isActive) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.surface
        val contentColor = if (isActive) MaterialTheme.colorScheme.onPrimary else MaterialTheme.colorScheme.onSurface
        val subContentColor =
            if (isActive) Color.White.copy(alpha = ALPHA_0_7) else MaterialTheme.colorScheme.onSurface.copy(alpha = ALPHA_0_5)

        Card(
            modifier = Modifier.width(width_110),
            colors = CardDefaults.cardColors(containerColor = backgroundColor),
            elevation = CardDefaults.cardElevation(if (isActive) elevation_4 else elevation_1),
            onClick = { onCardClick(students) },
        ) {
            Column(
                modifier = Modifier.padding(padding_12),
                verticalArrangement = Arrangement.spacedBy(space_8),
            ) {
                Text(
                    text = time.toString(),
                    style = MaterialTheme.typography.labelSmall,
                    color = subContentColor,
                )
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(space_4),
                ) {
                    Icon(
                        imageVector = if (students.size == 1) Icons.Default.Person else Icons.Default.Groups,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.secondary,
                        modifier = Modifier.size(size_16),
                    )
                    Text(
                        text =
                            if (students.size == 1) {
                                stringResource(Res.string.professor_detail_screen_individual_student, students.size)
                            } else {
                                stringResource(Res.string.professor_detail_screen_multiple_students, students.size)
                            },
                        style = MaterialTheme.typography.labelMedium,
                        fontWeight = FontWeight.Bold,
                        color = contentColor,
                        textAlign = TextAlign.Center,
                    )
                }
                Column {
                    LinearProgressIndicator(
                        // Calcula el progreso de 0.0 a 1.0. El coerceIn evita que se pase de 1f si hay 9 o 10 alumnos.
                        progress = (students.size / 8f).coerceIn(0f, 1f),
                        modifier = Modifier.fillMaxWidth(),
                        color =
                            when (students.size) {
                                in 0..5 -> GreenNormalCapacity
                                6, 7 -> YellowChargedCapacity
                                else -> RedOverloadedCapacity
                            },
                        trackColor = contentColor.copy(alpha = ALPHA_0_2),
                    )
                    Text(
                        modifier = Modifier.fillMaxWidth(),
                        text =
                            when (students.size) {
                                in 0..5 -> stringResource(Res.string.professor_detail_screen_daily_schedule_normal_capacity)
                                6, 7 -> stringResource(Res.string.professor_detail_screen_daily_schedule_charged_capacity)
                                else -> stringResource(Res.string.professor_detail_screen_daily_schedule_overloaded_capacity)
                            },
                        style = MaterialTheme.typography.labelSmall,
                        color = subContentColor,
                        textAlign = TextAlign.Center,
                    )
                }
            }
        }
    }
}

@Preview
@Composable
private fun ProfessorCardPreview() {
    CemedeTheme {
        CemedeCard.ProfessorCard(
            professor =
                Professor(
                    id = 4090,
                    name = "Amelia Caldwell",
                    isWorking = true,
                ),
            onCardClick = {},
            onCallButtonClick = {},
            onMessageButtonClick = {},
            onScheduleButtonClick = {},
        )
    }
}

@Preview
@Composable
private fun ProfessorDetailCardPreview() {
    CemedeTheme {
        CemedeCard.ProfessorDetailCard(
            professor =
                Professor(
                    id = 4090,
                    name = "Amelia Caldwell",
                    isWorking = true,
                ),
            onCallButtonClick = {},
            onMessageButtonClick = {},
        )
    }
}

@Preview
@Composable
private fun StudentCardPreview() {
    CemedeTheme {
        CemedeCard.StudentCard(
            student =
                Student(
                    id = 4090,
                    name = "Amelia Caldwell",
                    processType = "Readaptacion",
                ),
            onCardClick = { },
        )
    }
}

@Preview
@Composable
private fun WeeklyScheduleCard() {
    CemedeTheme {
        Row(
            horizontalArrangement = Arrangement.spacedBy(space_12),
        ) {
            Column(
                verticalArrangement = Arrangement.spacedBy(space_12),
            ) {
                CemedeCard.WeeklyScheduleCard(
                    time = LocalTime(12, 0, 0),
                    students =
                        listOf(
                            Student(id = 1, name = "Juan Pérez", processType = "Readaptacion"),
                        ),
                    now = DateTimeHandler.getCurrentDateTimeInfo(),
                    onCardClick = {},
                )
                CemedeCard.WeeklyScheduleCard(
                    time = LocalTime(DateTimeHandler.getCurrentDateTimeInfo().time.hour, 0, 0),
                    students =
                        listOf(
                            Student(id = 1, name = "Juan Pérez", processType = "Readaptacion"),
                        ),
                    now = DateTimeHandler.getCurrentDateTimeInfo(),
                    onCardClick = {},
                )
            }
            Column(
                verticalArrangement = Arrangement.spacedBy(space_12),
            ) {
                CemedeCard.WeeklyScheduleCard(
                    time = LocalTime(12, 0, 0),
                    students =
                        listOf(
                            Student(id = 1, name = "Juan Pérez", processType = "Readaptacion"),
                            Student(id = 1, name = "Juan Pérez", processType = "Readaptacion"),
                            Student(id = 1, name = "Juan Pérez", processType = "Readaptacion"),
                            Student(id = 1, name = "Juan Pérez", processType = "Readaptacion"),
                            Student(id = 1, name = "Juan Pérez", processType = "Readaptacion"),
                            Student(id = 1, name = "Juan Pérez", processType = "Readaptacion"),
                        ),
                    now = DateTimeHandler.getCurrentDateTimeInfo(),
                    onCardClick = {},
                )
                CemedeCard.WeeklyScheduleCard(
                    time = LocalTime(DateTimeHandler.getCurrentDateTimeInfo().time.hour, 0, 0),
                    students =
                        listOf(
                            Student(id = 1, name = "Juan Pérez", processType = "Readaptacion"),
                            Student(id = 1, name = "Juan Pérez", processType = "Readaptacion"),
                            Student(id = 1, name = "Juan Pérez", processType = "Readaptacion"),
                            Student(id = 1, name = "Juan Pérez", processType = "Readaptacion"),
                            Student(id = 1, name = "Juan Pérez", processType = "Readaptacion"),
                            Student(id = 1, name = "Juan Pérez", processType = "Readaptacion"),
                        ),
                    now = DateTimeHandler.getCurrentDateTimeInfo(),
                    onCardClick = {},
                )
            }
            Column(
                verticalArrangement = Arrangement.spacedBy(space_12),
            ) {
                CemedeCard.WeeklyScheduleCard(
                    time = LocalTime(12, 0, 0),
                    students =
                        listOf(
                            Student(id = 1, name = "Juan Pérez", processType = "Readaptacion"),
                            Student(id = 1, name = "Juan Pérez", processType = "Readaptacion"),
                            Student(id = 1, name = "Juan Pérez", processType = "Readaptacion"),
                            Student(id = 1, name = "Juan Pérez", processType = "Readaptacion"),
                            Student(id = 1, name = "Juan Pérez", processType = "Readaptacion"),
                            Student(id = 1, name = "Juan Pérez", processType = "Readaptacion"),
                            Student(id = 1, name = "Juan Pérez", processType = "Readaptacion"),
                            Student(id = 1, name = "Juan Pérez", processType = "Readaptacion"),
                            Student(id = 1, name = "Juan Pérez", processType = "Readaptacion"),
                        ),
                    now = DateTimeHandler.getCurrentDateTimeInfo(),
                    onCardClick = {},
                )
                CemedeCard.WeeklyScheduleCard(
                    time = LocalTime(DateTimeHandler.getCurrentDateTimeInfo().time.hour, 0, 0),
                    students =
                        listOf(
                            Student(id = 1, name = "Juan Pérez", processType = "Readaptacion"),
                            Student(id = 1, name = "Juan Pérez", processType = "Readaptacion"),
                            Student(id = 1, name = "Juan Pérez", processType = "Readaptacion"),
                            Student(id = 1, name = "Juan Pérez", processType = "Readaptacion"),
                            Student(id = 1, name = "Juan Pérez", processType = "Readaptacion"),
                            Student(id = 1, name = "Juan Pérez", processType = "Readaptacion"),
                            Student(id = 1, name = "Juan Pérez", processType = "Readaptacion"),
                            Student(id = 1, name = "Juan Pérez", processType = "Readaptacion"),
                            Student(id = 1, name = "Juan Pérez", processType = "Readaptacion"),
                        ),
                    now = DateTimeHandler.getCurrentDateTimeInfo(),
                    onCardClick = {},
                )
            }
        }
    }
}
