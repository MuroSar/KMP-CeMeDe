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
import androidx.compose.foundation.layout.fillMaxSize
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
import androidx.compose.material.icons.filled.MedicalServices
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
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
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import cemede.composeapp.generated.resources.Res
import cemede.composeapp.generated.resources.cemede_logo
import cemede.composeapp.generated.resources.contact
import cemede.composeapp.generated.resources.objective
import cemede.composeapp.generated.resources.partner_detail_screen_assigned_professor
import cemede.composeapp.generated.resources.partner_detail_screen_clinical_info
import cemede.composeapp.generated.resources.partner_detail_screen_principal_diagnosis
import cemede.composeapp.generated.resources.partner_detail_screen_principal_diagnosis_fallback
import cemede.composeapp.generated.resources.partner_detail_screen_related_syndrome
import cemede.composeapp.generated.resources.partner_detail_screen_related_syndrome_fallback
import cemede.composeapp.generated.resources.partner_detail_screen_working_plan
import cemede.composeapp.generated.resources.staff_member_card_action_call
import cemede.composeapp.generated.resources.staff_member_card_action_message
import cemede.composeapp.generated.resources.staff_member_card_action_schedule
import cemede.composeapp.generated.resources.staff_member_card_id
import cemede.composeapp.generated.resources.staff_member_card_name
import cemede.composeapp.generated.resources.staff_member_card_photo_content_description
import cemede.composeapp.generated.resources.staff_member_detail_screen_daily_schedule_charged_capacity
import cemede.composeapp.generated.resources.staff_member_detail_screen_daily_schedule_normal_capacity
import cemede.composeapp.generated.resources.staff_member_detail_screen_daily_schedule_overloaded_capacity
import cemede.composeapp.generated.resources.staff_member_detail_screen_individual_partner
import cemede.composeapp.generated.resources.staff_member_detail_screen_multiple_partners
import cemede.composeapp.generated.resources.stethoscope
import com.cemede.cemede.domain.model.DayOfWeek
import com.cemede.cemede.domain.model.Partner
import com.cemede.cemede.domain.model.ScheduleType
import com.cemede.cemede.domain.model.StaffMember
import com.cemede.cemede.domain.util.DateTimeHandler
import com.cemede.cemede.presentation.theme.ALPHA_0_05
import com.cemede.cemede.presentation.theme.ALPHA_0_1
import com.cemede.cemede.presentation.theme.ALPHA_0_2
import com.cemede.cemede.presentation.theme.ALPHA_0_5
import com.cemede.cemede.presentation.theme.ALPHA_0_7
import com.cemede.cemede.presentation.theme.BrownCharcoal
import com.cemede.cemede.presentation.theme.CemedeTheme
import com.cemede.cemede.presentation.theme.DarkSage
import com.cemede.cemede.presentation.theme.GreenNormalCapacity
import com.cemede.cemede.presentation.theme.LightSage
import com.cemede.cemede.presentation.theme.PartnerDetailGreen
import com.cemede.cemede.presentation.theme.RedOverloadedCapacity
import com.cemede.cemede.presentation.theme.WEIGHT_1
import com.cemede.cemede.presentation.theme.YellowChargedCapacity
import com.cemede.cemede.presentation.theme.elevation_0
import com.cemede.cemede.presentation.theme.elevation_1
import com.cemede.cemede.presentation.theme.elevation_2
import com.cemede.cemede.presentation.theme.elevation_4
import com.cemede.cemede.presentation.theme.height_16
import com.cemede.cemede.presentation.theme.height_200
import com.cemede.cemede.presentation.theme.height_48
import com.cemede.cemede.presentation.theme.height_8
import com.cemede.cemede.presentation.theme.letter_spacing_1
import com.cemede.cemede.presentation.theme.line_height_20
import com.cemede.cemede.presentation.theme.padding_10
import com.cemede.cemede.presentation.theme.padding_12
import com.cemede.cemede.presentation.theme.padding_16
import com.cemede.cemede.presentation.theme.padding_2
import com.cemede.cemede.presentation.theme.padding_20
import com.cemede.cemede.presentation.theme.padding_24
import com.cemede.cemede.presentation.theme.padding_8
import com.cemede.cemede.presentation.theme.size_0
import com.cemede.cemede.presentation.theme.size_12
import com.cemede.cemede.presentation.theme.size_14
import com.cemede.cemede.presentation.theme.size_150
import com.cemede.cemede.presentation.theme.size_16
import com.cemede.cemede.presentation.theme.size_18
import com.cemede.cemede.presentation.theme.size_20
import com.cemede.cemede.presentation.theme.size_24
import com.cemede.cemede.presentation.theme.size_44
import com.cemede.cemede.presentation.theme.size_48
import com.cemede.cemede.presentation.theme.size_56
import com.cemede.cemede.presentation.theme.size_80
import com.cemede.cemede.presentation.theme.space_12
import com.cemede.cemede.presentation.theme.space_16
import com.cemede.cemede.presentation.theme.space_24
import com.cemede.cemede.presentation.theme.space_4
import com.cemede.cemede.presentation.theme.space_8
import com.cemede.cemede.presentation.theme.width_110
import com.cemede.cemede.presentation.theme.width_16
import com.cemede.cemede.presentation.theme.width_2
import com.cemede.cemede.presentation.theme.width_4
import com.cemede.cemede.presentation.theme.width_8
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.LocalTime
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource

object CemedeCard {
    @Composable
    fun StaffMemberCard(
        modifier: Modifier = Modifier,
        staffMember: StaffMember,
        onCardClick: () -> Unit,
        onCallButtonClick: () -> Unit,
        onMessageButtonClick: () -> Unit,
        onScheduleButtonClick: () -> Unit,
    ) {
        val now = DateTimeHandler.getCurrentDateTimeInfo()
        val hourOnly = LocalTime(now.time.hour, 0)
        val today = DayOfWeek.valueOf(now.dayOfWeek.name)

        val isWorkingInThisMoment = staffMember.staffMemberWorkingSchedule[today]?.contains(hourOnly)

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
                            contentDescription = stringResource(Res.string.staff_member_card_photo_content_description, staffMember.name),
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
                        Box(
                            modifier =
                                Modifier
                                    .size(size_16)
                                    .background(
                                        color = if (isWorkingInThisMoment == true) Color.Green else Color.Red,
                                        shape = CircleShape,
                                    )
                                    .border(
                                        width = width_2,
                                        color = MaterialTheme.colorScheme.surface,
                                        shape = CircleShape,
                                    ).align(Alignment.BottomEnd),
                        )
                    }
                    Spacer(modifier = Modifier.padding(horizontal = padding_8))
                    Column {
                        Text(
                            text = stringResource(Res.string.staff_member_card_name, staffMember.name),
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold,
                        )
                        Text(
                            text = stringResource(Res.string.staff_member_card_id, staffMember.id),
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
                            text = stringResource(Res.string.staff_member_card_action_call),
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
                            text = stringResource(Res.string.staff_member_card_action_message),
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
                            text = stringResource(Res.string.staff_member_card_action_schedule),
                            style = MaterialTheme.typography.bodySmall,
                        )
                    }
                }
            }
        }
    }

    @Composable
    fun StaffMemberDetailCard(
        modifier: Modifier = Modifier,
        staffMember: StaffMember,
        onCallButtonClick: () -> Unit,
        onMessageButtonClick: () -> Unit,
    ) {
        val now = DateTimeHandler.getCurrentDateTimeInfo()
        val hourOnly = LocalTime(now.time.hour, 0)
        val today = DayOfWeek.valueOf(now.dayOfWeek.name)

        val isWorkingInThisMoment = staffMember.staffMemberWorkingSchedule[today]?.contains(hourOnly)

        Card(
            shape = RoundedCornerShape(size_0),
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
                            contentDescription = "Profile picture of ${staffMember.name}",
                            modifier =
                                Modifier
                                    .size(size_80)
                                    .clip(CircleShape)
                                    .border(
                                        width = width_2,
                                        color = MaterialTheme.colorScheme.secondary,
                                        shape = CircleShape,
                                    )
                                    .padding(padding_2)
                                    .clip(CircleShape),
                            contentScale = ContentScale.Crop,
                        )

                        Box(
                            modifier =
                                Modifier
                                    .size(size_20)
                                    .background(
                                        color = if (isWorkingInThisMoment == true) Color.Green else Color.Red,
                                        shape = CircleShape,
                                    )
                                    .border(
                                        width = width_2,
                                        color = MaterialTheme.colorScheme.surface,
                                        shape = CircleShape,
                                    ).align(Alignment.BottomEnd),
                        )
                    }

                    Column(modifier = Modifier.weight(WEIGHT_1)) {
                        Text(
                            text = stringResource(Res.string.staff_member_card_name, staffMember.name),
                            style = MaterialTheme.typography.headlineSmall,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.primary,
                        )
                        Text(
                            modifier = Modifier.padding(bottom = padding_12),
                            text = stringResource(Res.string.staff_member_card_id, staffMember.id),
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
                                    text = stringResource(Res.string.staff_member_card_action_message).uppercase(),
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
                                    text = stringResource(Res.string.staff_member_card_action_call).uppercase(),
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
    fun PartnerCard(
        modifier: Modifier = Modifier,
        partner: Partner,
        onCardClick: (Partner) -> Unit,
    ) {
        Card(
            modifier = modifier.fillMaxWidth(),
            onClick = { onCardClick(partner) },
        ) {
            Row(
                modifier = Modifier.padding(padding_12),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(space_12),
            ) {
                Image(
                    painter = painterResource(Res.drawable.cemede_logo),
                    contentDescription = "Foto de perfil de ${partner.name}",
                    modifier = Modifier.size(size_44).clip(CircleShape),
                )
                Column(modifier = Modifier.weight(WEIGHT_1)) {
                    Text(
                        text = partner.name,
                        style = MaterialTheme.typography.bodyMedium,
                        fontWeight = FontWeight.Bold,
                    )
                    Text(
                        text = partner.processType,
                        style = MaterialTheme.typography.labelSmall,
                        color = MaterialTheme.colorScheme.onSurface.copy(alpha = ALPHA_0_7),
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
        partners: List<Partner>,
        now: LocalDateTime,
        onCardClick: (partners: List<Partner>) -> Unit,
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
            onClick = { onCardClick(partners) },
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
                        imageVector = if (partners.size == 1) Icons.Default.Person else Icons.Default.Groups,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.secondary,
                        modifier = Modifier.size(size_16),
                    )
                    Text(
                        text =
                            if (partners.size == 1) {
                                stringResource(Res.string.staff_member_detail_screen_individual_partner, partners.size)
                            } else {
                                stringResource(Res.string.staff_member_detail_screen_multiple_partners, partners.size)
                            },
                        style = MaterialTheme.typography.labelMedium,
                        fontWeight = FontWeight.Bold,
                        color = contentColor,
                        textAlign = TextAlign.Center,
                    )
                }
                Column {
                    LinearProgressIndicator(
                        progress = (partners.size / 8f).coerceIn(0f, 1f),
                        modifier = Modifier.fillMaxWidth(),
                        color =
                            when (partners.size) {
                                in 0..5 -> GreenNormalCapacity
                                6, 7 -> YellowChargedCapacity
                                else -> RedOverloadedCapacity
                            },
                        trackColor = contentColor.copy(alpha = ALPHA_0_2),
                    )
                    Text(
                        modifier = Modifier.fillMaxWidth(),
                        text =
                            when (partners.size) {
                                in 0..5 -> stringResource(Res.string.staff_member_detail_screen_daily_schedule_normal_capacity)
                                6, 7 -> stringResource(Res.string.staff_member_detail_screen_daily_schedule_charged_capacity)
                                else -> stringResource(Res.string.staff_member_detail_screen_daily_schedule_overloaded_capacity)
                            },
                        style = MaterialTheme.typography.labelSmall,
                        color = subContentColor,
                        textAlign = TextAlign.Center,
                    )
                }
            }
        }
    }

    @Composable
    fun ClinicalInfoCard(partner: Partner) {
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(size_24),
            elevation = CardDefaults.cardElevation(elevation_0)
        ) {
            Box(modifier = Modifier.padding(padding_24)) {
                Icon(
                    imageVector = Icons.Default.MedicalServices,
                    contentDescription = null,
                    modifier = Modifier
                        .size(size_80)
                        .align(Alignment.TopEnd)
                        .alpha(ALPHA_0_05),
                    tint = Color.Black
                )

                Column(verticalArrangement = Arrangement.spacedBy(space_24)) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(space_8)
                    ) {
                        Icon(
                            painter = painterResource(Res.drawable.stethoscope),
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.primary,
                        )
                        Text(
                            text = stringResource(Res.string.partner_detail_screen_clinical_info),
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.primary,
                        )
                    }

                    Row(modifier = Modifier.fillMaxWidth()) {
                        Column(
                            modifier = Modifier.weight(WEIGHT_1),
                            verticalArrangement = Arrangement.spacedBy(space_4),
                        ) {
                            Text(
                                text = stringResource(Res.string.partner_detail_screen_principal_diagnosis),
                                style = MaterialTheme.typography.labelSmall,
                                fontWeight = FontWeight.Bold,
                            )
                            Text(
                                text = partner.diagnosis.ifBlank { stringResource(Res.string.partner_detail_screen_principal_diagnosis_fallback) },
                                style = MaterialTheme.typography.bodyMedium,
                                color = MaterialTheme.colorScheme.onSurface.copy(alpha = ALPHA_0_7),
                                lineHeight = line_height_20
                            )
                        }
                        Spacer(modifier = Modifier.width(width_16))
                        Column(
                            modifier = Modifier.weight(WEIGHT_1),
                            verticalArrangement = Arrangement.spacedBy(space_4),
                        ) {
                            Text(
                                text = stringResource(Res.string.partner_detail_screen_related_syndrome),
                                style = MaterialTheme.typography.labelSmall,
                                fontWeight = FontWeight.Bold,
                            )
                            Text(
                                text = partner.syndrome.ifBlank { stringResource(Res.string.partner_detail_screen_related_syndrome_fallback) },
                                style = MaterialTheme.typography.bodyMedium,
                                color = MaterialTheme.colorScheme.onSurface.copy(alpha = ALPHA_0_7),
                                lineHeight = line_height_20
                            )
                        }
                    }
                }
            }
        }
    }

    @Composable
    fun AssignedProfessorCard(
        modifier: Modifier = Modifier,
        staffMemberName: String,
        onButtonClicked: () -> Unit,
    ) {
        Card(
            modifier = modifier.fillMaxWidth(),
            shape = RoundedCornerShape(size_24),
            colors = CardDefaults.cardColors(containerColor = PartnerDetailGreen)
        ) {
            Column(
                modifier = Modifier.padding(padding_20).fillMaxSize(),
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                Column {
                    Text(
                        text = stringResource(Res.string.partner_detail_screen_assigned_professor),
                        style = MaterialTheme.typography.labelSmall,
                        fontWeight = FontWeight.Bold,
                        letterSpacing = letter_spacing_1,
                        color = MaterialTheme.colorScheme.onSurface.copy(alpha = ALPHA_0_7),
                    )
                    Spacer(modifier = Modifier.height(height_16))
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(space_12),
                    ) {
                        Image(
                            painter = painterResource(Res.drawable.cemede_logo),
                            contentDescription = null,
                            modifier = Modifier
                                .size(size_48)
                                .clip(CircleShape)
                                .border(
                                    width = width_2,
                                    color = LightSage,
                                    shape = CircleShape,
                                ),
                            contentScale = ContentScale.Crop
                        )
                        Text(
                            text = stringResource(Res.string.staff_member_card_name, staffMemberName),
                            style = MaterialTheme.typography.bodyLarge,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.onSurface,
                        )
                    }
                }

                Button(
                    onClick = onButtonClicked,
                    modifier = Modifier.fillMaxWidth(),
                    colors = ButtonDefaults.buttonColors(containerColor = Color.White.copy(alpha = ALPHA_0_1)),
                    shape = RoundedCornerShape(size_12)
                ) {
                    Icon(
                        modifier = Modifier.size(size_16),
                        imageVector = Icons.Default.ChatBubble,
                        contentDescription = "Ícono de mensaje",
                        tint = MaterialTheme.colorScheme.onSurface
                    )
                    Spacer(modifier = Modifier.width(width_8))
                    Text(
                        text = stringResource(Res.string.contact),
                        style = MaterialTheme.typography.labelMedium,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onSurface,
                    )
                }
            }
        }
    }

    @Composable
    fun WorkPlanCard(
        modifier: Modifier = Modifier,
        objective: String,
    ) {
        Card(
            modifier = modifier.fillMaxWidth(),
            shape = RoundedCornerShape(size_24),
            colors = CardDefaults.cardColors(containerColor = BrownCharcoal)
        ) {
            Box(modifier = Modifier.fillMaxSize()) {
                // Background Blur Effect
                Box(
                    modifier = Modifier
                        .size(size_150)
                        .align(Alignment.TopEnd)
                        .background(
                            color = DarkSage.copy(alpha = ALPHA_0_1),
                            shape = CircleShape,
                        )
                )

                Column(
                    modifier = Modifier
                        .padding(padding_20)
                        .fillMaxSize(),
                ) {
                    Text(
                        text = stringResource(Res.string.partner_detail_screen_working_plan),
                        style = MaterialTheme.typography.labelSmall,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.primary,
                        letterSpacing = letter_spacing_1
                    )
                    Spacer(modifier = Modifier.height(height_16))
                    Text(
                        text = stringResource(Res.string.objective),
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onSurface,
                    )
                    Spacer(modifier = Modifier.height(height_8))
                    Text(
                        text = "\"$objective\"",
                        style = MaterialTheme.typography.bodyLarge,
                        color = MaterialTheme.colorScheme.onSurface.copy(alpha = ALPHA_0_7),
                        fontStyle = FontStyle.Italic
                    )
                }
            }
        }
    }
}

@Preview
@Composable
private fun StaffMemberCardPreview() {
    CemedeTheme {
        CemedeCard.StaffMemberCard(
            staffMember =
                StaffMember(
                    id = 4090,
                    name = "Amelia Caldwell",
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
private fun StaffMemberDetailCardPreview() {
    CemedeTheme {
        CemedeCard.StaffMemberDetailCard(
            staffMember =
                StaffMember(
                    id = 4090,
                    name = "Amelia Caldwell",
                ),
            onCallButtonClick = {},
            onMessageButtonClick = {},
        )
    }
}

@Preview
@Composable
private fun PartnerCardPreview() {
    CemedeTheme {
        CemedeCard.PartnerCard(
            partner =
                Partner(
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
                    partners =
                        listOf(
                            Partner(id = 1, name = "Juan Pérez", processType = "Readaptacion"),
                        ),
                    now = DateTimeHandler.getCurrentDateTimeInfo(),
                    onCardClick = {},
                )
                CemedeCard.WeeklyScheduleCard(
                    time = LocalTime(DateTimeHandler.getCurrentDateTimeInfo().time.hour, 0, 0),
                    partners =
                        listOf(
                            Partner(id = 1, name = "Juan Pérez", processType = "Readaptacion"),
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
                    partners =
                        listOf(
                            Partner(id = 1, name = "Juan Pérez", processType = "Readaptacion"),
                            Partner(id = 1, name = "Juan Pérez", processType = "Readaptacion"),
                            Partner(id = 1, name = "Juan Pérez", processType = "Readaptacion"),
                            Partner(id = 1, name = "Juan Pérez", processType = "Readaptacion"),
                            Partner(id = 1, name = "Juan Pérez", processType = "Readaptacion"),
                            Partner(id = 1, name = "Juan Pérez", processType = "Readaptacion"),
                        ),
                    now = DateTimeHandler.getCurrentDateTimeInfo(),
                    onCardClick = {},
                )
                CemedeCard.WeeklyScheduleCard(
                    time = LocalTime(DateTimeHandler.getCurrentDateTimeInfo().time.hour, 0, 0),
                    partners =
                        listOf(
                            Partner(id = 1, name = "Juan Pérez", processType = "Readaptacion"),
                            Partner(id = 1, name = "Juan Pérez", processType = "Readaptacion"),
                            Partner(id = 1, name = "Juan Pérez", processType = "Readaptacion"),
                            Partner(id = 1, name = "Juan Pérez", processType = "Readaptacion"),
                            Partner(id = 1, name = "Juan Pérez", processType = "Readaptacion"),
                            Partner(id = 1, name = "Juan Pérez", processType = "Readaptacion"),
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
                    partners =
                        listOf(
                            Partner(id = 1, name = "Juan Pérez", processType = "Readaptacion"),
                            Partner(id = 1, name = "Juan Pérez", processType = "Readaptacion"),
                            Partner(id = 1, name = "Juan Pérez", processType = "Readaptacion"),
                            Partner(id = 1, name = "Juan Pérez", processType = "Readaptacion"),
                            Partner(id = 1, name = "Juan Pérez", processType = "Readaptacion"),
                            Partner(id = 1, name = "Juan Pérez", processType = "Readaptacion"),
                            Partner(id = 1, name = "Juan Pérez", processType = "Readaptacion"),
                            Partner(id = 1, name = "Juan Pérez", processType = "Readaptacion"),
                            Partner(id = 1, name = "Juan Pérez", processType = "Readaptacion"),
                        ),
                    now = DateTimeHandler.getCurrentDateTimeInfo(),
                    onCardClick = {},
                )
                CemedeCard.WeeklyScheduleCard(
                    time = LocalTime(DateTimeHandler.getCurrentDateTimeInfo().time.hour, 0, 0),
                    partners =
                        listOf(
                            Partner(id = 1, name = "Juan Pérez", processType = "Readaptacion"),
                            Partner(id = 1, name = "Juan Pérez", processType = "Readaptacion"),
                            Partner(id = 1, name = "Juan Pérez", processType = "Readaptacion"),
                            Partner(id = 1, name = "Juan Pérez", processType = "Readaptacion"),
                            Partner(id = 1, name = "Juan Pérez", processType = "Readaptacion"),
                            Partner(id = 1, name = "Juan Pérez", processType = "Readaptacion"),
                            Partner(id = 1, name = "Juan Pérez", processType = "Readaptacion"),
                            Partner(id = 1, name = "Juan Pérez", processType = "Readaptacion"),
                            Partner(id = 1, name = "Juan Pérez", processType = "Readaptacion"),
                        ),
                    now = DateTimeHandler.getCurrentDateTimeInfo(),
                    onCardClick = {},
                )
            }
        }
    }
}

@Preview
@Composable
private fun ClinicalInfoCardPreview() {
    CemedeTheme {
        CemedeCard.ClinicalInfoCard(
            Partner(
                id = 7607,
                name = "Jaime Keith",
                entryDate = null,
                processType = "mentitum",
                syndrome = "invenire",
                diagnosis = "expetenda",
                staffMemberName = "Noah Barton",
                scheduleType = ScheduleType.FIXED,
                workingSchedule = mapOf()

            )
        )
    }
}

@Preview
@Composable
private fun AssignedProfessorCardPreview() {
    CemedeTheme {
        CemedeCard.AssignedProfessorCard(
            staffMemberName = "Macarena",
            onButtonClicked = { }
        )
    }
}

@Preview
@Composable
private fun WorkPlanCardPreview() {
    CemedeTheme {
        CemedeCard.WorkPlanCard(
            objective = "Readaptación"
        )
    }
}
