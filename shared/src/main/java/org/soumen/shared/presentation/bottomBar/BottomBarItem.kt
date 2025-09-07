package org.soumen.shared.presentation.bottomBar

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.NavigationBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import org.soumen.shared.domain.Resources

sealed class BottomBarItem(
    val icon: Int,
    val label: String,
    val route: String
) {
    data object Home : BottomBarItem(
        icon = Resources.Icons.home,
        label = "Home",
        route = "/home"
    )

    data object WatchList : BottomBarItem(
        icon = Resources.Icons.bookmark,
        label = "Watchlist",
        route = "/watchlist"
    )
}

//Reason to keet the routes string rather than serializable objects or classes because of the proguard and ninify


@Composable
fun BottomBar(
    items: List<BottomBarItem>,
    currentRoute: String?,
    onBottomBarClick: (String) -> Unit
) {
    NavigationBar(
        modifier = Modifier
            .clip(shape = RoundedCornerShape(topStart = 32.dp, topEnd = 32.dp)),
        containerColor = Resources.Colors.background2,
    ) {
        items.forEach { item ->
            val selected = currentRoute == item.route
            CustomNavigationBarItem(
                icon = painterResource(item.icon),
                label = item.label,
                selected = selected,
                onClick = { onBottomBarClick(item.route) },
                modifier = Modifier.Companion.weight(1f)
            )
        }
    }
}