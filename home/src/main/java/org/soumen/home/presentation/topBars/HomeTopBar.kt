package org.soumen.home.presentation.topBars

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.soumen.shared.domain.Resources

@Composable
fun HomeTopBar(
    modifier: Modifier = Modifier,
    onSearchClick : () -> Unit = {}
) {
    Row(
        modifier = Modifier
            .statusBarsPadding()
            .padding(horizontal = 16.dp)
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {

        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {

            Image(
                modifier = Modifier
                    .size(36.dp),
                painter = painterResource(Resources.Icons.appIcon),
                contentDescription = "App Icon",
            )

            Spacer(modifier = Modifier.width(8.dp))

            Text(
                text = "ETFlux",
                fontFamily = Resources.AppFont.dmSans,
                fontSize = 20.sp,
                color = Resources.Colors.textColor,
                fontWeight = FontWeight.Medium
            )

        }


        IconButton(
            onClick = {
                onSearchClick()
            },
        ) {
            Icon(
                modifier = Modifier.size(28.dp),
                imageVector = Icons.Default.Search,
                contentDescription = "Search Icon",
                tint = Resources.Colors.textColor
            )

        }


    }
}