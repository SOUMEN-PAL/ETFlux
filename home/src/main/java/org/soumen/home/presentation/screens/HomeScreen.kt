package org.soumen.home.presentation.screens

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.ripple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import org.soumen.core.utils.setStatusBarLightIcons
import org.soumen.home.domain.dataModels.Data
import org.soumen.home.presentation.bottomBar.BottomBar
import org.soumen.home.presentation.bottomBar.BottomBarItem
import org.soumen.home.presentation.states.HomeScreenDataState
import org.soumen.home.presentation.topBars.HomeTopBar
import org.soumen.home.presentation.viewmodels.HomeViewModel
import org.soumen.shared.domain.Resources

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    bottomBarItems: List<BottomBarItem>,
    homeViewModel: HomeViewModel,
    onBottomBarClick: (String) -> Unit = {},
    currentRoute: String?,
    onSearchClick: () -> Unit = {},
    onItemClick : (String) -> Unit = {_->},
    onGainersViewAllClick : () -> Unit = {},
    onLosersViewAllClick : () -> Unit = {}

) {
    DisposableEffect(Unit) {
        setStatusBarLightIcons(true)
        homeViewModel.getGainersAndLosersData()
        onDispose {

        }
    }

    val gainerAndLosersDataState = homeViewModel.homeScreenDataState.collectAsStateWithLifecycle()
    val imageDataState = homeViewModel.tickerImages.collectAsStateWithLifecycle()
    val interactionSource = remember { MutableInteractionSource() }
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
            HomeTopBar {
                onSearchClick()
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
                when (val state = gainerAndLosersDataState.value) {
                    is HomeScreenDataState.Error -> {
                        Toast.makeText(
                            LocalContext.current,
                            state.message,
                            Toast.LENGTH_LONG
                        ).show()
                    }

                    HomeScreenDataState.Loading -> {
                        LinearProgressIndicator(
                            modifier = Modifier.fillMaxWidth(),
                            color = Resources.Colors.ascentGreen,
                            trackColor = Resources.Colors.overlayColor
                        )
                    }

                    is HomeScreenDataState.Success -> {
                        val data = state.data
                        val gainers = data.topGainers.take(4)
                        val losers = data.topLosers.take(4)




                        LazyVerticalGrid(
                            columns = GridCells.Fixed(2),
                            horizontalArrangement = Arrangement.spacedBy(12.dp), // space between columns
                            verticalArrangement = Arrangement.spacedBy(12.dp),   // space between rows
                            contentPadding = PaddingValues(12.dp)               // padding around the grid
                        ) {
                            item(span = { GridItemSpan(2) }) {
                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {

                                    Text(
                                        text = "Top Gainers",
                                        color = Resources.Colors.textColor,
                                        fontFamily = Resources.AppFont.dmSans,
                                        fontWeight = FontWeight.Normal,
                                        fontSize = 18.sp
                                    )

                                    Row(
                                        modifier = Modifier
                                            .clickable(
                                                enabled = gainers.isNotEmpty(),
                                                onClick = {
                                                    onGainersViewAllClick()
                                                },
                                                indication = null,
                                                interactionSource = interactionSource
                                            )
                                    ) {
                                        Text(
                                            text = "View all",
                                            color = Resources.Colors.textColor,
                                            fontFamily = Resources.AppFont.dmSans,
                                            fontWeight = FontWeight.Normal,
                                            fontSize = 16.sp
                                        )

                                        Icon(
                                            imageVector = Icons.AutoMirrored.Filled.ArrowForward,
                                            contentDescription = "Forward Arrow",
                                            tint = Resources.Colors.textColor,
                                        )
                                    }

                                }
                            }

                            items(gainers, key = { it -> it.ticker }) { item ->
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
                                                onItemClick(item.ticker)
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


                            item(span = { GridItemSpan(2) }) {
                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {

                                    Text(
                                        text = "Top Losers",
                                        color = Resources.Colors.textColor,
                                        fontFamily = Resources.AppFont.dmSans,
                                        fontWeight = FontWeight.Normal,
                                        fontSize = 18.sp
                                    )

                                    Row(
                                        modifier = Modifier
                                            .clickable(
                                                enabled = gainers.isNotEmpty(),
                                                onClick = {
                                                    onLosersViewAllClick()
                                                },
                                                indication = null,
                                                interactionSource = interactionSource
                                            )
                                    ) {
                                        Text(
                                            text = "View all",
                                            color = Resources.Colors.textColor,
                                            fontFamily = Resources.AppFont.dmSans,
                                            fontWeight = FontWeight.Normal,
                                            fontSize = 16.sp
                                        )

                                        Icon(
                                            imageVector = Icons.AutoMirrored.Filled.ArrowForward,
                                            contentDescription = "Forward Arrow",
                                            tint = Resources.Colors.textColor,
                                        )
                                    }

                                }
                            }


                            items(losers, key = { it -> it.ticker }) { item ->
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
                                                onItemClick(item.ticker)
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