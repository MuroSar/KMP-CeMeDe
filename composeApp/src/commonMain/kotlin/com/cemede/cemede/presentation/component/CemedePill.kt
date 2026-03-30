package com.cemede.cemede.presentation.component

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import com.cemede.cemede.presentation.theme.ALPHA_0_1
import com.cemede.cemede.presentation.theme.CemedeTheme
import com.cemede.cemede.presentation.theme.padding_16
import com.cemede.cemede.presentation.theme.padding_8
import com.cemede.cemede.presentation.theme.size_20
import com.cemede.cemede.presentation.theme.space_12
import com.cemede.cemede.presentation.theme.width_1

@Composable
fun CemedePill(
    modifier: Modifier = Modifier,
    text: String,
    isSelected: Boolean,
    onClick: () -> Unit,
) {
    val backgroundColor = if (isSelected) {
        MaterialTheme.colorScheme.primary
    } else {
        MaterialTheme.colorScheme.primary.copy(alpha = ALPHA_0_1)
    }

    val contentColor = if (isSelected) {
        Color.White
    } else {
        MaterialTheme.colorScheme.primary
    }

    val borderColor = if (isSelected) {
        MaterialTheme.colorScheme.primary
    } else {
        Color.Transparent
    }

    Box(
        modifier = modifier
            .clip(RoundedCornerShape(size_20))
            .background(backgroundColor)
            .border(width_1, borderColor, RoundedCornerShape(size_20))
            .clickable { onClick() }
            .padding(horizontal = padding_16, vertical = padding_8)
    ) {
        Text(
            text = text,
            style = MaterialTheme.typography.labelMedium,
            fontWeight = FontWeight.Bold,
            color = contentColor
        )
    }
}

@Preview
@Composable
private fun CemedePillPreview() {
    CemedeTheme {
        Row(
            modifier = Modifier.padding(padding_16),
            horizontalArrangement = Arrangement.spacedBy(space_12)
        ) {
            CemedePill(
                text = "Seleccionado",
                isSelected = true,
                onClick = {}
            )
            CemedePill(
                text = "No seleccionado",
                isSelected = false,
                onClick = {}
            )
        }
    }
}
