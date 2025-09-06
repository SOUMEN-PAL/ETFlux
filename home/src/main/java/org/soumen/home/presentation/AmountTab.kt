package org.soumen.home.presentation

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.soumen.shared.domain.Resources

@Composable
fun AmountTab(
    selected: Boolean = true,
    onTap: (String) -> Unit = { _ -> },
    amount: String = "50"
) {
    val borderColor by animateColorAsState(
        targetValue = if (selected) Resources.Colors.ascentGreen else Resources.Colors.background2,
        label = "borderColor"
    )

    Box(
        modifier = Modifier
            .height(58.dp)
            .width(95.dp)
            .border(
                width = 1.dp,
                shape = RoundedCornerShape(12.dp),
                color = borderColor
            )
            .clip(RoundedCornerShape(12.dp))
            .clickable(
                indication = null,
                interactionSource = remember { MutableInteractionSource() },
                onClick = { onTap(amount) }
            )
    ) {
        // Animated text position
        AnimatedContent(
            targetState = selected,
            modifier = Modifier.align(Alignment.Center),
            transitionSpec = {
                fadeIn() togetherWith fadeOut()
            },
            label = "amountTextTransition"
        ) { isSelected ->
            Text(
                modifier = Modifier.padding(bottom = if (isSelected) 13.dp else 0.dp),
                text = amount,
                fontSize = 16.sp,
                color = Resources.Colors.textColor,
                fontWeight = FontWeight.Medium,
                fontFamily = Resources.AppFont.dmSans
            )
        }

        // Animated "Popular" label
        AnimatedVisibility(
            visible = selected,
            modifier = Modifier.align(Alignment.BottomCenter),
            enter = expandVertically() + fadeIn(),
            exit = shrinkVertically() + fadeOut()
        ) {
            Row(
                modifier = Modifier
                    .background(Resources.Colors.ascentGreen)
                    .fillMaxWidth()
            ) {
                Text(
                    modifier = Modifier.fillMaxWidth(),
                    text = "Selected",
                    color = Resources.Colors.background,
                    textAlign = TextAlign.Center,
                    fontSize = 12.sp
                )
            }
        }
    }
}