package org.soumen.watchlist.presentation

import android.widget.Toast
import androidx.activity.compose.BackHandler
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.ArrowForwardIos
import androidx.compose.material.icons.filled.ArrowForwardIos
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.outlined.BookmarkAdd
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import org.soumen.shared.domain.Resources
import org.soumen.shared.presentation.bottomBar.BottomBar
import org.soumen.shared.presentation.bottomBar.BottomBarItem
import org.soumen.watchlist.domain.datamodules.WatchlistData
import org.soumen.watchlist.presentation.states.WatchListDataState
import org.soumen.watchlist.presentation.viewmodel.WatchlistViewModel

@Composable
fun WatchlistScreen(
    modifier: Modifier = Modifier,
    bottomBarItems: List<BottomBarItem>,
    onBottomBarClick: (String) -> Unit = {},
    currentRoute: String?,
    watchlistViewModel: WatchlistViewModel,
    onBackClick: () -> Unit = {},
    onItemClick: (id: Long) -> Unit = {}
) {
    val context = LocalContext.current
    BackHandler(true) {
        onBackClick()
    }
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        bottomBar = {
            BottomBar(
                bottomBarItems,
                onBottomBarClick = onBottomBarClick,
                currentRoute = currentRoute
            )
        },
        topBar = {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .statusBarsPadding(),
                horizontalArrangement = Arrangement.Start,
                verticalAlignment = Alignment.CenterVertically
            ) {

                Row(
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    IconButton(
                        onClick = {
                            onBackClick()
                        }
                    ) {
                        Icon(
                            modifier = Modifier.size(24.dp),
                            imageVector = Icons.AutoMirrored.Default.ArrowBack,
                            contentDescription = "Back",
                            tint = Resources.Colors.textColor
                        )
                    }

                    Text(
                        text = "Watchlist",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.SemiBold,
                        fontFamily = Resources.AppFont.dmSans,
                        color = Resources.Colors.textColor
                    )


                }


            }
        }
    ) { innerPadding ->


        Column(
            modifier = modifier
                .fillMaxSize()
                .background(color = Resources.Colors.background),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Column(
                modifier = Modifier
                    .padding(innerPadding)
                    .padding(horizontal = 16.dp)
                    .fillMaxSize(),
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {


                val data = watchlistViewModel.watchlists.collectAsStateWithLifecycle()

                LazyColumn {
                    items(data.value, key = { it -> it.id }) { item ->
                        WatchlistItem(
                            modifier = Modifier,
                            data = item,
                            onClick = { id ->
                                onItemClick(id)
                            },
                            onDeleteClick = { id ->
                                watchlistViewModel.deleteWatchlist(id)
                            }
                        )
                        HorizontalDivider(
                            thickness = 2.dp,
                            color = Resources.Colors.background2,
                        )
                    }
                }


            }

        }

    }

}


@Composable
fun WatchlistItem(
    modifier: Modifier = Modifier,
    data: WatchlistData,
    onClick: (Long) -> Unit = { _ -> },
    onDeleteClick: (Long) -> Unit = { _ -> }
) {
    val showDeleteButton = rememberSaveable { mutableStateOf(false) }
    Row(
        modifier = modifier
            .combinedClickable(
                enabled = true,
                onClick = {
                    onClick(data.id)
                },
                onLongClick = {
                    showDeleteButton.value = !showDeleteButton.value
                }
            )
            .fillMaxWidth()
            .padding(vertical = 8.dp, horizontal = 16.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {

        Row(
            horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = data.name,
                fontSize = 20.sp,
                fontWeight = FontWeight.Medium,
                fontFamily = Resources.AppFont.dmSans,
                color = Resources.Colors.textColor,
            )
            Spacer(
                modifier = Modifier.width(8.dp)
            )
            AnimatedVisibility(
                visible = showDeleteButton.value,
            ) {

                Button(
                    onClick = {
                        onDeleteClick(data.id)
                        showDeleteButton.value = false
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Resources.Colors.ascentRed
                    )
                ) {

                    Row(
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically
                    ) {


                        Icon(
                            modifier = Modifier.size(24.dp),
                            imageVector = Icons.Default.Delete,
                            contentDescription = "Delete Watchlist",
                            tint = Resources.Colors.background,
                        )

                        Text(
                            text = "Delete",
                            color = Resources.Colors.background
                        )
                    }

                }


            }
        }

        IconButton(
            onClick = { onClick(data.id) }
        ) {
            Icon(
                modifier = Modifier.size(24.dp),
                imageVector = Icons.AutoMirrored.Filled.ArrowForwardIos,
                contentDescription = "check watchlist",
                tint = Resources.Colors.ascentGreen
            )
        }
    }
}