package org.soumen.shared.domain

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import org.soumen.shared.R

object Resources {
    object AppFont{
        val dmSans = FontFamily(
            Font(R.font.dmsans_regular, weight = FontWeight.Normal),
            Font(R.font.dmsans_medium, weight = FontWeight.Medium),
            Font(R.font.dmsans_bold, weight = FontWeight.Bold),
            Font(R.font.dmsans_semibold, weight = FontWeight.SemiBold),
            Font(R.font.dmsans_light, weight = FontWeight.Light),
            Font(R.font.dmsans_extralight, weight = FontWeight.ExtraLight),
            Font(R.font.dmsans_thin, weight = FontWeight.Thin),
            Font(R.font.dmsans_black, weight = FontWeight.Black),
            Font(R.font.dmsans_extrabold, weight = FontWeight.ExtraBold),

            // Italics
            Font(R.font.dmsans_italic, weight = FontWeight.Normal, style = FontStyle.Italic),
            Font(R.font.dmsans_mediumitalic, weight = FontWeight.Medium, style = FontStyle.Italic),
            Font(R.font.dmsans_bolditalic, weight = FontWeight.Bold, style = FontStyle.Italic),
            Font(R.font.dmsans_semibolditalic, weight = FontWeight.SemiBold, style = FontStyle.Italic),
            Font(R.font.dmsans_lightitalic, weight = FontWeight.Light, style = FontStyle.Italic),
            Font(R.font.dmsans_extralightitalic, weight = FontWeight.ExtraLight, style = FontStyle.Italic),
            Font(R.font.dmsans_thinitalic, weight = FontWeight.Thin, style = FontStyle.Italic),
            Font(R.font.dmsans_blackitalic, weight = FontWeight.Black, style = FontStyle.Italic),
            Font(R.font.dmsans_extrabolditalic, weight = FontWeight.ExtraBold, style = FontStyle.Italic),

            // Size-specific variants (18pt, 24pt, 36pt) — optional
            Font(R.font.dmsans_18pt_regular, weight = FontWeight.Normal),
            Font(R.font.dmsans_18pt_medium, weight = FontWeight.Medium),
            Font(R.font.dmsans_18pt_bold, weight = FontWeight.Bold),
            Font(R.font.dmsans_18pt_semibold, weight = FontWeight.SemiBold),
            Font(R.font.dmsans_18pt_light, weight = FontWeight.Light),
            Font(R.font.dmsans_18pt_extralight, weight = FontWeight.ExtraLight),
            Font(R.font.dmsans_18pt_thin, weight = FontWeight.Thin),
            Font(R.font.dmsans_18pt_black, weight = FontWeight.Black),
            Font(R.font.dmsans_18pt_extrabold, weight = FontWeight.ExtraBold),

            Font(R.font.dmsans_18pt_italic, weight = FontWeight.Normal, style = FontStyle.Italic),
            Font(R.font.dmsans_18pt_mediumitalic, weight = FontWeight.Medium, style = FontStyle.Italic),
            Font(R.font.dmsans_18pt_bolditalic, weight = FontWeight.Bold, style = FontStyle.Italic),
            Font(R.font.dmsans_18pt_semibolditalic, weight = FontWeight.SemiBold, style = FontStyle.Italic),
            Font(R.font.dmsans_18pt_lightitalic, weight = FontWeight.Light, style = FontStyle.Italic),
            Font(R.font.dmsans_18pt_extralightitalic, weight = FontWeight.ExtraLight, style = FontStyle.Italic),
            Font(R.font.dmsans_18pt_thinitalic, weight = FontWeight.Thin, style = FontStyle.Italic),
            Font(R.font.dmsans_18pt_blackitalic, weight = FontWeight.Black, style = FontStyle.Italic),
            Font(R.font.dmsans_18pt_extrabolditalic, weight = FontWeight.ExtraBold, style = FontStyle.Italic),

            Font(R.font.dmsans_24pt_regular, weight = FontWeight.Normal),
            Font(R.font.dmsans_24pt_medium, weight = FontWeight.Medium),
            Font(R.font.dmsans_24pt_bold, weight = FontWeight.Bold),
            Font(R.font.dmsans_24pt_semibold, weight = FontWeight.SemiBold),
            Font(R.font.dmsans_24pt_light, weight = FontWeight.Light),
            Font(R.font.dmsans_24pt_extralight, weight = FontWeight.ExtraLight),
            Font(R.font.dmsans_24pt_thin, weight = FontWeight.Thin),
            Font(R.font.dmsans_24pt_black, weight = FontWeight.Black),
            Font(R.font.dmsans_24pt_extrabold, weight = FontWeight.ExtraBold),

            Font(R.font.dmsans_24pt_italic, weight = FontWeight.Normal, style = FontStyle.Italic),
            Font(R.font.dmsans_24pt_mediumitalic, weight = FontWeight.Medium, style = FontStyle.Italic),
            Font(R.font.dmsans_24pt_bolditalic, weight = FontWeight.Bold, style = FontStyle.Italic),
            Font(R.font.dmsans_24pt_semibolditalic, weight = FontWeight.SemiBold, style = FontStyle.Italic),
            Font(R.font.dmsans_24pt_lightitalic, weight = FontWeight.Light, style = FontStyle.Italic),
            Font(R.font.dmsans_24pt_extralightitalic, weight = FontWeight.ExtraLight, style = FontStyle.Italic),
            Font(R.font.dmsans_24pt_thinitalic, weight = FontWeight.Thin, style = FontStyle.Italic),
            Font(R.font.dmsans_24pt_blackitalic, weight = FontWeight.Black, style = FontStyle.Italic),
            Font(R.font.dmsans_24pt_extrabolditalic, weight = FontWeight.ExtraBold, style = FontStyle.Italic),

            Font(R.font.dmsans_36pt_regular, weight = FontWeight.Normal),
            Font(R.font.dmsans_36pt_medium, weight = FontWeight.Medium),
            Font(R.font.dmsans_36pt_bold, weight = FontWeight.Bold),
            Font(R.font.dmsans_36pt_semibold, weight = FontWeight.SemiBold),
            Font(R.font.dmsans_36pt_light, weight = FontWeight.Light),
            Font(R.font.dmsans_36pt_extralight, weight = FontWeight.ExtraLight),
            Font(R.font.dmsans_36pt_thin, weight = FontWeight.Thin),
            Font(R.font.dmsans_36pt_black, weight = FontWeight.Black),
            Font(R.font.dmsans_36pt_extrabold, weight = FontWeight.ExtraBold),

            Font(R.font.dmsans_36pt_italic, weight = FontWeight.Normal, style = FontStyle.Italic),
            Font(R.font.dmsans_36pt_mediumitalic, weight = FontWeight.Medium, style = FontStyle.Italic),
            Font(R.font.dmsans_36pt_bolditalic, weight = FontWeight.Bold, style = FontStyle.Italic),
            Font(R.font.dmsans_36pt_semibolditalic, weight = FontWeight.SemiBold, style = FontStyle.Italic),
            Font(R.font.dmsans_36pt_lightitalic, weight = FontWeight.Light, style = FontStyle.Italic),
            Font(R.font.dmsans_36pt_extralightitalic, weight = FontWeight.ExtraLight, style = FontStyle.Italic),
            Font(R.font.dmsans_36pt_thinitalic, weight = FontWeight.Thin, style = FontStyle.Italic),
            Font(R.font.dmsans_36pt_blackitalic, weight = FontWeight.Black, style = FontStyle.Italic),
            Font(R.font.dmsans_36pt_extrabolditalic, weight = FontWeight.ExtraBold, style = FontStyle.Italic),
        )
    }

    object Colors{
        val background = Color.White
        val background2 = Color(0xFFE8E7EA)
        val ascentRed = Color(0xFFE34C3B)
        val ascentGreen = Color(0xFF3ECF9C)
        val textColor = Color(0xFF36383A)
        val overlayColor = Color(0xFF534E8B)
    }

    object Icons{
        val bookmark = R.drawable.bookmark
        val home = R.drawable.home
    }
}