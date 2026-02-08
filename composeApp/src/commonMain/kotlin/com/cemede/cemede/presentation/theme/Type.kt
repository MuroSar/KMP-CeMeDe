package com.cemede.cemede.presentation.theme

import androidx.compose.material3.Typography
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import cemede.composeapp.generated.resources.Lexend_Black
import cemede.composeapp.generated.resources.Lexend_Bold
import cemede.composeapp.generated.resources.Lexend_ExtraBold
import cemede.composeapp.generated.resources.Lexend_ExtraLight
import cemede.composeapp.generated.resources.Lexend_Light
import cemede.composeapp.generated.resources.Lexend_Medium
import cemede.composeapp.generated.resources.Lexend_Regular
import cemede.composeapp.generated.resources.Lexend_SemiBold
import cemede.composeapp.generated.resources.Lexend_Thin
import cemede.composeapp.generated.resources.Res
import org.jetbrains.compose.resources.Font

@Composable
fun lexendFontFamily() = FontFamily(
    Font(Res.font.Lexend_Thin, FontWeight.Thin),
    Font(Res.font.Lexend_ExtraLight, FontWeight.ExtraLight),
    Font(Res.font.Lexend_Light, FontWeight.Light),
    Font(Res.font.Lexend_Regular, FontWeight.Normal),
    Font(Res.font.Lexend_Medium, FontWeight.Medium),
    Font(Res.font.Lexend_SemiBold, FontWeight.SemiBold),
    Font(Res.font.Lexend_Bold, FontWeight.Bold),
    Font(Res.font.Lexend_ExtraBold, FontWeight.ExtraBold),
    Font(Res.font.Lexend_Black, FontWeight.Black),
)

@Composable
fun getTypography(): Typography {
    val lexend = lexendFontFamily()
    return Typography(
        displayLarge = androidx.compose.ui.text.TextStyle(fontFamily = lexend),
        displayMedium = androidx.compose.ui.text.TextStyle(fontFamily = lexend),
        displaySmall = androidx.compose.ui.text.TextStyle(fontFamily = lexend),
        headlineLarge = androidx.compose.ui.text.TextStyle(fontFamily = lexend),
        headlineMedium = androidx.compose.ui.text.TextStyle(fontFamily = lexend),
        headlineSmall = androidx.compose.ui.text.TextStyle(fontFamily = lexend),
        titleLarge = androidx.compose.ui.text.TextStyle(fontFamily = lexend),
        titleMedium = androidx.compose.ui.text.TextStyle(fontFamily = lexend),
        titleSmall = androidx.compose.ui.text.TextStyle(fontFamily = lexend),
        bodyLarge = androidx.compose.ui.text.TextStyle(fontFamily = lexend),
        bodyMedium = androidx.compose.ui.text.TextStyle(fontFamily = lexend),
        bodySmall = androidx.compose.ui.text.TextStyle(fontFamily = lexend),
        labelLarge = androidx.compose.ui.text.TextStyle(fontFamily = lexend),
        labelMedium = androidx.compose.ui.text.TextStyle(fontFamily = lexend),
        labelSmall = androidx.compose.ui.text.TextStyle(fontFamily = lexend),
    )
}
