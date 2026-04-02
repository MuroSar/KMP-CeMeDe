package com.cemede.cemede.presentation.screen.partner_detail

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material.icons.filled.CreditCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import cemede.composeapp.generated.resources.Res
import cemede.composeapp.generated.resources.back
import cemede.composeapp.generated.resources.partner_detail_screen_entry_date
import cemede.composeapp.generated.resources.partner_detail_screen_header_title
import cemede.composeapp.generated.resources.partner_detail_screen_partner_id
import com.cemede.cemede.domain.model.DayOfWeek
import com.cemede.cemede.domain.model.Partner
import com.cemede.cemede.domain.util.DateTimeHandler
import com.cemede.cemede.presentation.component.CemedeCard
import com.cemede.cemede.presentation.component.CemedeInfoBadge
import com.cemede.cemede.presentation.component.CemedeScheduleTable
import com.cemede.cemede.presentation.component.CemedeTopAppBar
import com.cemede.cemede.presentation.theme.ALPHA_0_7
import com.cemede.cemede.presentation.theme.CemedeTheme
import com.cemede.cemede.presentation.theme.PartnerDetailGreen
import com.cemede.cemede.presentation.theme.TechWhite
import com.cemede.cemede.presentation.theme.WEIGHT_1
import com.cemede.cemede.presentation.theme.font_size_24
import com.cemede.cemede.presentation.theme.padding_16
import com.cemede.cemede.presentation.theme.size_14
import com.cemede.cemede.presentation.theme.space_16
import com.cemede.cemede.presentation.theme.space_4
import com.cemede.cemede.presentation.theme.space_8
import com.cemede.cemede.presentation.util.PhonesHelper
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalTime
import org.jetbrains.compose.resources.stringResource

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PartnerDetailScreen(
    partner: Partner,
    onNavigateBack: () -> Unit,
) {
    CemedeTheme {
        Scaffold(
            topBar = {
                CemedeTopAppBar.TopAppBar(
                    title = stringResource(Res.string.partner_detail_screen_header_title),
                    navigationIcon = {
                        IconButton(onClick = onNavigateBack) {
                            Icon(
                                imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                                contentDescription = stringResource(Res.string.back),
                            )
                        }
                    }
                )
            },
        ) { paddingValues ->
            Column(
                modifier = Modifier
                    .padding(paddingValues)
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
                    .padding(padding_16),
                verticalArrangement = Arrangement.spacedBy(space_16)
            ) {
                PartnerHeader(partner)

                CemedeCard.ClinicalInfoCard(partner)

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(space_16)
                ) {
                    CemedeCard.AssignedProfessorCard(
                        modifier = Modifier.weight(WEIGHT_1),
                        staffMemberName = partner.staffMemberName,
                        onButtonClicked = { PhonesHelper.openWhatsApp(partner.staffMemberName) }
                    )
                    CemedeCard.WorkPlanCard(
                        modifier = Modifier.weight(WEIGHT_1),
                        objective = partner.processType
                    )
                }

                CemedeScheduleTable.PartnerSchedule(partner)
            }
        }
    }
}

@Composable
private fun PartnerHeader(partner: Partner) {
    Column(verticalArrangement = Arrangement.spacedBy(space_4)) {
        Text(
            text = partner.name,
            style = MaterialTheme.typography.headlineLarge,
            fontWeight = FontWeight.Black,
            color = MaterialTheme.colorScheme.primary,
            fontSize = font_size_24,
        )
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(space_4)
        ) {
            Icon(
                imageVector = Icons.Default.CalendarMonth,
                contentDescription = null,
                modifier = Modifier.size(size_14),
                tint = MaterialTheme.colorScheme.onSurface.copy(alpha = ALPHA_0_7)
            )
            Text(
                text = stringResource(Res.string.partner_detail_screen_entry_date, DateTimeHandler.formatDate(partner.entryDate)),
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurface.copy(alpha = ALPHA_0_7),
                fontWeight = FontWeight.Medium
            )
        }

        Spacer(modifier = Modifier.height(space_8))

        CemedeInfoBadge.InfoBadge(
            icon = Icons.Default.CreditCard,
            text = stringResource(Res.string.partner_detail_screen_partner_id, partner.id),
            containerColor = TechWhite,
            contentColor = PartnerDetailGreen
        )
    }
}

@Preview(showBackground = true, name = "Full Data Variant")
@Composable
private fun PartnerDetailScreenPreview() {
    val samplePartner = Partner(
        id = 1234,
        name = "Mauro Sarti",
        entryDate = LocalDate(2023, 5, 15),
        processType = "Readaptación Deportiva",
        syndrome = "Síndrome de Pinzamiento Femoracetabular",
        diagnosis = "Desgarro de labrum anterior con osteofitos asociados en cadera derecha",
        staffMemberName = "Macarena",
        workingSchedule = mapOf(
            DayOfWeek.MONDAY to LocalTime(15, 0),
            DayOfWeek.WEDNESDAY to LocalTime(15, 0)
        )
    )
    PartnerDetailScreen(partner = samplePartner, onNavigateBack = {})
}

@Preview(showBackground = true, name = "Empty Data Variant")
@Composable
private fun PartnerDetailScreenEmptyPreview() {
    val emptyPartner = Partner(
        id = 5678,
        name = "Socio Sin Datos",
        processType = "Salud",
        staffMemberName = "Pendiente",
        workingSchedule = emptyMap()
    )
    PartnerDetailScreen(partner = emptyPartner, onNavigateBack = {})
}

@Preview(showBackground = true, name = "Long Text Variant")
@Composable
private fun PartnerDetailScreenLongTextPreview() {
    val longPartner = Partner(
        id = 9999,
        name = "Juan Ignacio de la Santísima Trinidad Rodríguez",
        entryDate = LocalDate(2022, 1, 1),
        processType = "Entrenamiento Funcional de Alto Rendimiento",
        syndrome = "Espondilolistesis grado II L5-S1 con compromiso radicular bilateral",
        diagnosis = "Hernia de disco extruída con migración caudal y estenosis foraminal severa",
        staffMemberName = "Francisco Javier",
        workingSchedule = mapOf(
            DayOfWeek.TUESDAY to LocalTime(8, 30),
            DayOfWeek.THURSDAY to LocalTime(8, 30),
            DayOfWeek.SATURDAY to LocalTime(10, 0)
        )
    )
    PartnerDetailScreen(partner = longPartner, onNavigateBack = {})
}
