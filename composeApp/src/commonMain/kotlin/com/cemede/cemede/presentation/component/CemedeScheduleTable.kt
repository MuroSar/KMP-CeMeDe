package com.cemede.cemede.presentation.component

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Schedule
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import cemede.composeapp.generated.resources.Res
import cemede.composeapp.generated.resources.day
import cemede.composeapp.generated.resources.partner_detail_screen_schedule
import cemede.composeapp.generated.resources.partner_detail_screen_schedule_fallback
import cemede.composeapp.generated.resources.time
import com.cemede.cemede.domain.model.DayOfWeek
import com.cemede.cemede.domain.model.Partner
import com.cemede.cemede.domain.model.ScheduleType
import com.cemede.cemede.domain.model.StaffMember
import com.cemede.cemede.presentation.theme.CamouflageOliveGreen
import com.cemede.cemede.presentation.theme.CemedeTheme
import com.cemede.cemede.presentation.theme.GreenNormalCapacity
import com.cemede.cemede.presentation.theme.PartnerDetailGreen
import com.cemede.cemede.presentation.theme.RedOverloadedCapacity
import com.cemede.cemede.presentation.theme.ScheduleTableHeaderBackground
import com.cemede.cemede.presentation.theme.TechWhite
import com.cemede.cemede.presentation.theme.WEIGHT_1
import com.cemede.cemede.presentation.theme.YellowChargedCapacity
import com.cemede.cemede.presentation.theme.elevation_0
import com.cemede.cemede.presentation.theme.height_24
import com.cemede.cemede.presentation.theme.padding_16
import com.cemede.cemede.presentation.theme.padding_2
import com.cemede.cemede.presentation.theme.padding_24
import com.cemede.cemede.presentation.theme.padding_32
import com.cemede.cemede.presentation.theme.padding_4
import com.cemede.cemede.presentation.theme.padding_8
import com.cemede.cemede.presentation.theme.size_16
import com.cemede.cemede.presentation.theme.size_24
import com.cemede.cemede.presentation.theme.size_4
import com.cemede.cemede.presentation.theme.space_2
import com.cemede.cemede.presentation.theme.space_8
import com.cemede.cemede.presentation.theme.width_1
import com.cemede.cemede.presentation.theme.width_100
import com.cemede.cemede.presentation.theme.width_80
import kotlinx.datetime.LocalTime
import org.jetbrains.compose.resources.stringResource

object CemedeScheduleTable {
    @Composable
    fun PartnerSchedule(partner: Partner) {
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(size_24),
            colors = CardDefaults.cardColors(containerColor = Color.White),
            elevation = CardDefaults.cardElevation(elevation_0)
        ) {
            Column(modifier = Modifier.padding(padding_24)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(space_8)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Schedule,
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.primary,
                        )
                        Text(
                            text = stringResource(Res.string.partner_detail_screen_schedule),
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold,
                            color = CamouflageOliveGreen
                        )
                    }
                }

                Spacer(modifier = Modifier.height(height_24))

                Column(
                    modifier = Modifier
                        .border(
                            width = width_1,
                            color = TechWhite,
                            shape = RoundedCornerShape(size_16),
                        )
                        .clip(RoundedCornerShape(size_16))
                ) {
                    ScheduleHeader()
                    HorizontalDivider(
                        color = TechWhite,
                        thickness = width_1,
                    )
                    partner.workingSchedule.forEach { (day, time) -> ScheduleRow(day.displayName, time.toString()) }
                    if (partner.workingSchedule.isEmpty()) {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(padding_32),
                            contentAlignment = Alignment.Center,
                        ) {
                            Text(
                                text = stringResource(Res.string.partner_detail_screen_schedule_fallback),
                                color = Color.Gray,
                                style = MaterialTheme.typography.bodySmall,
                            )
                        }
                    }
                }
            }
        }
    }

    @Composable
    private fun ScheduleHeader() {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(ScheduleTableHeaderBackground)
                .padding(padding_16),
            horizontalArrangement = Arrangement.spacedBy(space_8)
        ) {
            Text(
                text = stringResource(Res.string.day),
                modifier = Modifier.weight(WEIGHT_1),
                style = MaterialTheme.typography.labelSmall,
                fontWeight = FontWeight.Bold,
                color = Color.Gray
            )
            Text(
                text = stringResource(Res.string.time),
                modifier = Modifier.weight(WEIGHT_1),
                style = MaterialTheme.typography.labelSmall,
                fontWeight = FontWeight.Bold,
                color = Color.Gray
            )
        }
    }

    @Composable
    private fun ScheduleRow(day: String, time: String) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(padding_16),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(space_8)
        ) {
            Text(
                day,
                modifier = Modifier.weight(WEIGHT_1),
                style = MaterialTheme.typography.bodySmall,
                fontWeight = FontWeight.Bold,
                color = PartnerDetailGreen
            )
            Text(
                text = time,
                modifier = Modifier.weight(WEIGHT_1),
                style = MaterialTheme.typography.bodySmall, color = Color.DarkGray,
            )
        }
    }

    @Composable
    fun StaffSchedule(staffMembers: List<StaffMember>) {
        val days = DayOfWeek.laborDays()
        val allTimes = staffMembers.flatMap { it.partnersSchedule.values.flatMap { it.keys } }.distinct().sorted()
        val scrollState = rememberScrollState()

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .border(
                    width = width_1,
                    color = TechWhite,
                    shape = RoundedCornerShape(size_16),
                )
                .clip(RoundedCornerShape(size_16))
                .background(Color.White)
        ) {
            Column(
                modifier = Modifier
                    .horizontalScroll(scrollState)
                    .width(IntrinsicSize.Max)
                    .padding(bottom = padding_16),
            ) {
                // Header Row (Days)
                Row(
                    modifier = Modifier
                        .background(ScheduleTableHeaderBackground)
                        .padding(padding_8)
                ) {
                    Box(
                        modifier = Modifier
                            .width(width_80)
                            .padding(padding_8),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = stringResource(Res.string.time).lowercase().capitalize(),
                            style = MaterialTheme.typography.labelSmall,
                            fontWeight = FontWeight.Bold,
                            color = Color.Gray
                        )
                    }
                    days.forEach { day ->
                        Box(
                            modifier = Modifier
                                .width(width_100)
                                .padding(padding_8),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = day.displayName,
                                style = MaterialTheme.typography.labelSmall,
                                fontWeight = FontWeight.Bold,
                                color = Color.Gray
                            )
                        }
                    }
                }

                HorizontalDivider(
                    color = TechWhite,
                    thickness = width_1,
                )

                // Data Rows (Times)
                allTimes.forEach { time ->
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        // Time Column
                        Box(
                            modifier = Modifier
                                .width(width_80)
                                .padding(padding_8),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = time.toString().take(5),
                                style = MaterialTheme.typography.bodySmall,
                                fontWeight = FontWeight.Bold,
                                color = PartnerDetailGreen
                            )
                        }

                        // Days Columns
                        days.forEach { day ->
                            Column(
                                modifier = Modifier
                                    .width(width_100)
                                    .padding(padding_4),
                                horizontalAlignment = Alignment.CenterHorizontally,
                                verticalArrangement = Arrangement.spacedBy(space_2)
                            ) {
                                staffMembers.forEach { staff ->
                                    val count = staff.partnersSchedule[day]?.get(time)?.size ?: 0
                                    if (count > 0) {
                                        val capacityColor =
                                            when (count) {
                                                in 0..5 -> GreenNormalCapacity
                                                6, 7 -> YellowChargedCapacity
                                                else -> RedOverloadedCapacity
                                            }

                                        Text(
                                            text = "${staff.name.split(" ").firstOrNull() ?: ""}: $count",
                                            style = MaterialTheme.typography.labelSmall,
                                            fontWeight = FontWeight.Bold,
                                            color = Color.DarkGray,
                                            textAlign = TextAlign.Center,
                                            modifier = Modifier
                                                .fillMaxWidth()
                                                .background(
                                                    capacityColor.copy(alpha = 0.5f),
                                                    RoundedCornerShape(size_4)
                                                )
                                                .padding(padding_2)
                                        )
                                    }
                                }
                            }
                        }
                    }
                    HorizontalDivider(
                        color = TechWhite,
                        thickness = width_1,
                    )
                }
            }
        }
    }
}

@Preview
@Composable
private fun PartnerSchedulePreview() {
    CemedeTheme {
        CemedeScheduleTable.PartnerSchedule(
            partner = Partner(
                id = 6214,
                name = "Marian Beck",
                entryDate = null,
                processType = "vulputate",
                syndrome = "vis",
                diagnosis = "verear",
                staffMemberName = "Hans Ellison",
                scheduleType = ScheduleType.FIXED,
                workingSchedule = mapOf(
                    DayOfWeek.MONDAY to LocalTime(15, 0),
                    DayOfWeek.WEDNESDAY to LocalTime(15, 0),
                    DayOfWeek.FRIDAY to LocalTime(15, 0)
                )

            )
        )
    }
}

@Preview
@Composable
private fun PartnerScheduleFallbackPreview() {
    CemedeTheme {
        CemedeScheduleTable.PartnerSchedule(
            partner = Partner(
                id = 6214,
                name = "Marian Beck",
                entryDate = null,
                processType = "vulputate",
                syndrome = "vis",
                diagnosis = "verear",
                staffMemberName = "Hans Ellison",
                scheduleType = ScheduleType.FIXED,
                workingSchedule = mapOf()
            )
        )
    }
}

@Preview
@Composable
private fun StaffSchedulePreview() {
    val partner = Partner(
        id = 1,
        name = "Socio",
        processType = "",
    )
    CemedeTheme {
        CemedeScheduleTable.StaffSchedule(
            staffMembers = listOf(
                StaffMember(
                    id = 1,
                    name = "Santiago",
                    partnersSchedule = mapOf(
                        DayOfWeek.MONDAY to mapOf(LocalTime(14, 0) to listOf(partner)),
                        DayOfWeek.WEDNESDAY to mapOf(LocalTime(15, 0) to listOf(partner))
                    )
                ),
                StaffMember(
                    id = 2,
                    name = "Mauro",
                    partnersSchedule = mapOf(
                        DayOfWeek.MONDAY to mapOf(LocalTime(14, 0) to List(6) { partner })
                    )
                ),
                StaffMember(
                    id = 3,
                    name = "Nacho",
                    partnersSchedule = mapOf(
                        DayOfWeek.MONDAY to mapOf(LocalTime(14, 0) to List(9) { partner })
                    )
                )
            )
        )
    }
}
