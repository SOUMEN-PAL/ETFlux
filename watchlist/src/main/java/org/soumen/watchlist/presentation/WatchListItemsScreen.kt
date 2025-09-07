package org.soumen.watchlist.presentation

import android.widget.Toast
import androidx.activity.compose.BackHandler
import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.ripple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import org.soumen.home.domain.dataModels.Data
import org.soumen.home.presentation.screens.HomeItems
import org.soumen.home.presentation.viewmodels.HomeViewModel
import org.soumen.shared.domain.Resources
import org.soumen.shared.presentation.bottomBar.BottomBar
import org.soumen.watchlist.presentation.states.WatchListDataState
import org.soumen.watchlist.presentation.viewmodel.WatchlistViewModel

@Composable
fun WatchListItemsScreen(
    modifier: Modifier = Modifier,
    watchListId: Long,
    watchlistViewModel: WatchlistViewModel,
    homeViewModel: HomeViewModel,
    onBackClick : () -> Unit = {},
    onItemClick : (Data) -> Unit = {},
) {
    val watchlistDataState = watchlistViewModel.watchListDataState.collectAsStateWithLifecycle()
    val imageDataState = homeViewModel.tickerImages.collectAsStateWithLifecycle()
    DisposableEffect(Unit) {
        watchlistViewModel.getAllItemsInWatchlist(watchListId)
        onDispose {

        }
    }

    val context = LocalContext.current

    BackHandler(true) {
        onBackClick()
    }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
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

            AnimatedContent(targetState = watchlistDataState.value){state->
                Column(
                    modifier = Modifier
                        .padding(innerPadding)
                        .padding(horizontal = 16.dp)
                        .fillMaxSize(),
                    verticalArrangement = Arrangement.Top,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    when(state){
                        WatchListDataState.Empty -> {
                            Text(text = "No items in this watchlist. Add some!" , color = Resources.Colors.textColor)
                        }
                        WatchListDataState.Error -> {
                            Text(text = "Something went wrong. Please try again." , color = Resources.Colors.textColor)
                            Toast.makeText(context , "Something went wrong. Please try again." , Toast.LENGTH_LONG).show()
                        }
                        WatchListDataState.Loading -> {
                            LinearProgressIndicator(
                                modifier = Modifier.fillMaxWidth(),
                                color = Resources.Colors.ascentGreen,
                                trackColor = Resources.Colors.overlayColor
                            )
                        }
                        is WatchListDataState.Success -> {
                            val data = state.data
                            LazyVerticalGrid(
                                columns = GridCells.Fixed(2),
                                horizontalArrangement = Arrangement.spacedBy(12.dp), // space between columns
                                verticalArrangement = Arrangement.spacedBy(12.dp),   // space between rows
                                contentPadding = PaddingValues(12.dp)               // padding around the grid
                            ) {

                                items(data, key = { it -> it.ticker }) { item ->
                                    HomeItems(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .aspectRatio(1f) // makes height = width
                                            .border(
                                                1.dp,
                                                Resources.Colors.ascentGreen,
                                                shape = RoundedCornerShape(12.dp)
                                            )
                                            .clip(RoundedCornerShape(12.dp))
                                            .clickable(
                                                enabled = true,
                                                onClick = {
                                                    onItemClick(item)
                                                },
                                                indication = ripple(
                                                    color = Resources.Colors.ascentGreen,
                                                    bounded = true
                                                ),
                                                interactionSource = remember { MutableInteractionSource() }
                                            )
                                            .padding(8.dp),
                                        imageURL = imageDataState.value[item.ticker] ?: "",
                                        data = item
                                    )
                                }

                            }
                        }
                    }


                }
            }
        }

    }


}