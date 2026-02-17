package com.cemede.cemede.presentation.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.filled.ChatBubble
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import cemede.composeapp.generated.resources.Res
import cemede.composeapp.generated.resources.cemede_logo
import cemede.composeapp.generated.resources.professor_card_action_call
import cemede.composeapp.generated.resources.professor_card_action_message
import cemede.composeapp.generated.resources.professor_card_action_schedule
import cemede.composeapp.generated.resources.professor_card_id
import cemede.composeapp.generated.resources.professor_card_name
import cemede.composeapp.generated.resources.professor_card_photo_content_description
import com.cemede.cemede.domain.model.Professor
import com.cemede.cemede.presentation.theme.ALPHA_0_7
import com.cemede.cemede.presentation.theme.CemedeTheme
import com.cemede.cemede.presentation.theme.elevation_2
import com.cemede.cemede.presentation.theme.height_48
import com.cemede.cemede.presentation.theme.padding_16
import com.cemede.cemede.presentation.theme.padding_2
import com.cemede.cemede.presentation.theme.padding_8
import com.cemede.cemede.presentation.theme.size_16
import com.cemede.cemede.presentation.theme.size_18
import com.cemede.cemede.presentation.theme.size_56
import com.cemede.cemede.presentation.theme.width_2
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
