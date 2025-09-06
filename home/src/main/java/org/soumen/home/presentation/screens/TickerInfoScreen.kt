package org.soumen.home.presentation.screens

import android.util.Log
import android.widget.Toast
import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.outlined.Bookmark
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil3.compose.AsyncImage
import org.soumen.home.presentation.states.TickerDataState
import org.soumen.home.presentation.states.TickerMonthlyDataState
import org.soumen.home.presentation.viewmodels.HomeViewModel
import org.soumen.shared.domain.Resources


@Composable
fun TickerInfoScreen(
    modifier: Modifier = Modifier,
    ticker : String,
    viewModel: HomeViewModel,
) {

    val tickerDataState = viewModel.tickerDataState.collectAsState()
    val tickerImage = viewModel.tickerImages.collectAsStateWithLifecycle()
    val tickerMonthlyDataState = viewModel.tickerMonthlyData.collectAsStateWithLifecycle()
    val hasLoadedMonthly = remember(ticker) { mutableStateOf(false) }
    var limit by rememberSaveable {
        mutableIntStateOf(6)
    }
    DisposableEffect(Unit) {
        viewModel.getTickerInfo(ticker)
        viewModel.getTickerImage(ticker)

        onDispose {  }
    }


    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            Row(modifier = Modifier
                .padding(horizontal = 16.dp)
                .statusBarsPadding()
                .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ){
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ){
                    IconButton(
                        onClick = {

                        }
                    ) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Default.ArrowBack,
                            contentDescription = "BAck",
                            tint = Resources.Colors.textColor
                        )
                    }

                    Text(
                        text = "Detail Screen",
                        fontFamily = Resources.AppFont.dmSans,
                        fontSize = 20.sp,
                        color = Resources.Colors.textColor,
                        fontWeight = FontWeight.Medium
                    )
                }


                IconButton(
                    onClick = {

                    }
                ) {
                    Icon(
                        imageVector = Icons.Filled.Bookmark,
                        contentDescription = "Bookmark",
                        tint = Color.Magenta
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

                AnimatedContent(targetState = tickerDataState.value) {state->
                    when(state){
                        is TickerDataState.Error -> {

                        }
                        TickerDataState.Loading -> {
                            LinearProgressIndicator(
                                modifier = Modifier.fillMaxWidth(),
                                color = Resources.Colors.ascentGreen,
                                trackColor = Resources.Colors.overlayColor
                            )
                        }
                        is TickerDataState.Success -> {
                            LaunchedEffect(hasLoadedMonthly.value) {
                                if(!hasLoadedMonthly.value){
                                    hasLoadedMonthly.value = true
                                    viewModel.getMonthlyData(ticker , limit)
                                }
                            }
                            val data = state.data
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {

                                val image = tickerImage.value[ticker]

                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.Center
                                ){
                                    AsyncImage(
                                        modifier = Modifier.size(60.dp),
                                        model = image,
                                        contentDescription = "Image",
                                        contentScale = ContentScale.FillBounds
                                    )
                                    Spacer(
                                        modifier = Modifier.width(8.dp)
                                    )
                                    Column(
                                        verticalArrangement = Arrangement.Center,
                                        horizontalAlignment = Alignment.Start,
                                    ) {
                                        Text(
                                            text = data.Name,
                                            fontFamily = Resources.AppFont.dmSans,
                                            fontSize = 16.sp,
                                            color = Resources.Colors.textColor,
                                            fontWeight = FontWeight.SemiBold
                                        )

                                        Text(
                                            text = data.Exchange,
                                            fontFamily = Resources.AppFont.dmSans,
                                            fontSize = 16.sp,
                                            color = Resources.Colors.ascentGreen,
                                            fontWeight = FontWeight.Medium
                                        )
                                    }
                                }

                                Text(
                                    text = data.Currency,
                                    fontFamily = Resources.AppFont.dmSans,
                                    fontSize = 20.sp,
                                    color = Resources.Colors.textColor,
                                    fontWeight = FontWeight.SemiBold
                                )
                            }

                            Column(
                                modifier = Modifier
                                    .fillMaxWidth(),
                                verticalArrangement = Arrangement.Top,
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                AnimatedContent(tickerMonthlyDataState.value) {state->
                                    when(state){
                                        is TickerMonthlyDataState.Error -> {
                                            Toast.makeText(
                                                LocalContext.current,
                                                state.e,
                                                Toast.LENGTH_SHORT
                                            ).show()
                                        }
                                        TickerMonthlyDataState.Loading -> {
                                            LinearProgressIndicator(
                                                modifier = Modifier.fillMaxWidth(),
                                                color = Resources.Colors.ascentGreen,
                                                trackColor = Resources.Colors.overlayColor
                                            )
                                        }
                                        is TickerMonthlyDataState.Success -> {
                                            val monthLyData = state.data




                                        }
                                    }
                                }


                            }
                        }
                    }

                }





            }

        }
    }
}