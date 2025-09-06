package org.soumen.home.presentation.screens

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
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
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
import org.soumen.home.presentation.states.GainerDataState
import org.soumen.home.presentation.viewmodels.HomeViewModel
import org.soumen.shared.domain.Resources

@Composable
fun AllLosersScreen(
    modifier: Modifier = Modifier,
    homeViewModel: HomeViewModel,
    onBackClick: () -> Unit = {},
    onItemClick: (String) -> Unit = {}
) {
    BackHandler(true) {
        onBackClick()
    }
    val loserDataState = homeViewModel.loserListState.collectAsStateWithLifecycle()
    val imageDataState = homeViewModel.tickerImages.collectAsStateWithLifecycle()

    DisposableEffect(Unit) {
        homeViewModel.getAllLosers()
        onDispose {

        }
    }

    val gridState = rememberLazyGridState()


    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            Row(
                modifier = Modifier
                    .padding(horizontal = 16.dp)
                    .statusBarsPadding()
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Start
            ) {
                IconButton(
                    onClick = {
                        onBackClick()
                    }
                ) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Default.ArrowBack,
                        contentDescription = "BAck",
                        tint = Resources.Colors.textColor
                    )
                }

                Text(
                    text = "Top Losers",
                    fontFamily = Resources.AppFont.dmSans,
                    fontSize = 20.sp,
                    color = Resources.Colors.textColor,
                    fontWeight = FontWeight.Medium
                )
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

                AnimatedContent(targetState = loserDataState.value) { state ->
                    when (state) {
                        is GainerDataState.Error -> {
                            Toast.makeText(
                                LocalContext.current,
                                state.e,
                                Toast.LENGTH_LONG
                            ).show()
                        }

                        GainerDataState.Loading -> {
                            LinearProgressIndicator(
                                modifier = Modifier.fillMaxWidth(),
                                color = Resources.Colors.ascentGreen,
                                trackColor = Resources.Colors.overlayColor
                            )
                        }

                        is GainerDataState.Success -> {
                            LazyVerticalGrid(
                                state = gridState,
                                columns = GridCells.Fixed(2),
                                horizontalArrangement = Arrangement.spacedBy(12.dp), // space between columns
                                verticalArrangement = Arrangement.spacedBy(12.dp),   // space between rows
                                contentPadding = PaddingValues(12.dp)               // padding around the grid
                            ) {

                                items(state.data, key = { it -> it.ticker }) { item ->
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


}