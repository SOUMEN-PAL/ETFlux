package org.soumen.home.presentation.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.ripple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
import org.soumen.home.domain.dataModels.Data
import org.soumen.shared.domain.Resources

@Composable
fun HomeItems(
    modifier: Modifier = Modifier,
    imageURL: String,
    data: Data
) {
    Box(
        modifier = modifier
    ) {

        AsyncImage(
            modifier = Modifier
                .align(Alignment.TopStart),
            model = imageURL,
            contentDescription = "Image",
        )

        Column(
            modifier = Modifier
                .align(Alignment.BottomStart),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.Start,
        ) {
            Text(
                text = data.ticker,
                color = Resources.Colors.textColor,
                fontFamily = Resources.AppFont.dmSans,
                fontWeight = FontWeight.Normal,
                fontSize = 16.sp
            )

            Text(
                text = data.price,
                color = Resources.Colors.textColor,
                fontFamily = Resources.AppFont.dmSans,
                fontWeight = FontWeight.Normal,
                fontSize = 16.sp
            )
        }

        Column(
            modifier = Modifier
                .align(Alignment.BottomEnd),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.End,
        ) {

            Image(
                modifier = Modifier
                    .size(44.dp),
                painter = painterResource(if (data.changePercentage.startsWith("-")) Resources.Icons.trendingDown else Resources.Icons.trendingUp),
                contentDescription = "Trend"
            )

            Text(
                text = data.changePercentage,
                color = if(data.changePercentage.startsWith("-")) Resources.Colors.ascentRed else Resources.Colors.ascentGreen,
                fontFamily = Resources.AppFont.dmSans,
                fontWeight = FontWeight.Normal,
                fontSize = 16.sp
            )

        }


    }
}

@Preview
@Composable
fun HomeItemsPreview() {
    val data = Data(
        changeAmount = "10.0",
        changePercentage = "1.0%",
        price = "1000.0",
        ticker = "BTC",
        volume = "100"
    )
    HomeItems(
        imageURL = "https://example.com/image.png",
        data = data,
        modifier = Modifier
            .size(200.dp)
            .border(1.dp , Resources.Colors.ascentGreen , shape = RoundedCornerShape(12.dp) )
            .clip(RoundedCornerShape(12.dp))
            .padding(8.dp)
    )
}