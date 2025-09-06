package org.soumen.home.presentation.bottomBar

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.animation.with
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.TransformOrigin
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.unit.dp
import org.soumen.shared.domain.Resources

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun AnimatedIcon(
    selected: Boolean,
    icon: Painter,
    label: String
) {
    val itemSize = 64.dp

    Box(
        modifier = Modifier.size(itemSize),
        contentAlignment = Alignment.Center
    ) {
        AnimatedContent(
            targetState = selected,
            transitionSpec = {
                fadeIn(animationSpec = tween(100)) + scaleIn(transformOrigin = TransformOrigin.Center) with
                        fadeOut(animationSpec = tween(200)) + scaleOut(transformOrigin = TransformOrigin.Center)
            },
            label = "IconTransition"
        ) { isSelected: Boolean ->
            if (!isSelected) {
                // Normal unselected state
                Column(
                    modifier = Modifier.fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Icon(
                        painter = icon,
                        contentDescription = label,
                        tint = Resources.Colors.textColor,
                        modifier = Modifier
                            .size(24.dp)
                    )
                    Spacer(modifier = Modifier.height(2.dp))
                    Text(
                        text = label,
                        color = Resources.Colors.textColor,
                        style = MaterialTheme.typography.labelSmall
                    )
                }
            } else {
                // Selected state with background highlight
                Box(
                    modifier = Modifier
                        .background(
                            color = Resources.Colors.textColor,
                            shape = RoundedCornerShape(16.dp)
                        )
                        .padding(4.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        painter = icon,
                        contentDescription = label,
                        tint = Color.White,
                        modifier = Modifier
                            .padding(12.dp)
                            .size(28.dp)
                    )
                }
            }
        }
    }
}
