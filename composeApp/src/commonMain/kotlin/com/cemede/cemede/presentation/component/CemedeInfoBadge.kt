package com.cemede.cemede.presentation.component

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CreditCard
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import cemede.composeapp.generated.resources.Res
import cemede.composeapp.generated.resources.partner_detail_screen_partner_id
import com.cemede.cemede.presentation.theme.ALPHA_0_1
import com.cemede.cemede.presentation.theme.CemedeDarkOliveGreen
import com.cemede.cemede.presentation.theme.CemedeTheme
import com.cemede.cemede.presentation.theme.PartnerDetailGreen
import com.cemede.cemede.presentation.theme.TechWhite
import com.cemede.cemede.presentation.theme.padding_12
import com.cemede.cemede.presentation.theme.padding_8
import com.cemede.cemede.presentation.theme.size_16
import com.cemede.cemede.presentation.theme.size_8
import com.cemede.cemede.presentation.theme.space_8
import com.cemede.cemede.presentation.theme.width_1
import org.jetbrains.compose.resources.stringResource

object CemedeInfoBadge {
    @Composable
    fun InfoBadge(
        icon: ImageVector,
        text: String,
        containerColor: Color,
        contentColor: Color,
    ) {
        Row(
            modifier = Modifier
                .background(
                    color = containerColor,
                    shape = RoundedCornerShape(size_8),
                )
                .border(
                    width = width_1,
                    color = contentColor.copy(alpha = ALPHA_0_1),
                    shape = RoundedCornerShape(size_8),
                )
                .padding(horizontal = padding_12, vertical = padding_8),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(space_8)
        ) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                modifier = Modifier.size(size_16),
                tint = contentColor
            )
            Text(
                text = text,
                style = MaterialTheme.typography.labelMedium,
                fontWeight = FontWeight.SemiBold,
                color = contentColor
            )
        }
    }
}

@Preview
@Composable
private fun CemedeInfoBadgePreview() {
    CemedeTheme {
        CemedeInfoBadge.InfoBadge(
            icon = Icons.Default.CreditCard,
            text = stringResource(Res.string.partner_detail_screen_partner_id, "9292"),
            containerColor = TechWhite,
            contentColor = PartnerDetailGreen
        )
    }
}
