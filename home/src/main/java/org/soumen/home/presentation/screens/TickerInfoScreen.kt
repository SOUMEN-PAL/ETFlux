package org.soumen.home.presentation.screens

import android.widget.Button
import android.widget.CheckBox
import android.widget.Toast
import androidx.activity.compose.BackHandler
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.outlined.Bookmark
import androidx.compose.material.icons.outlined.BookmarkAdd
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.rememberCoroutineScope
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
import com.patrykandpatrick.vico.core.cartesian.data.CartesianChartModelProducer
import com.patrykandpatrick.vico.core.cartesian.data.lineSeries
import kotlinx.coroutines.launch
import org.soumen.core.utils.setStatusBarLightIcons
import org.soumen.home.domain.dataModels.Data
import org.soumen.home.domain.dataModels.WatchListData
import org.soumen.home.presentation.AmountTab
import org.soumen.home.presentation.states.TickerDataState
import org.soumen.home.presentation.states.TickerMonthlyDataState
import org.soumen.home.presentation.viewmodels.HomeViewModel
import org.soumen.shared.domain.Resources


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TickerInfoScreen(
    modifier: Modifier = Modifier,
    ticker: Data,
    viewModel: HomeViewModel,
    onBackClick: () -> Unit = {}
) {

    val tickerDataState = viewModel.tickerDataState.collectAsState()
    val tickerImage = viewModel.tickerImages.collectAsStateWithLifecycle()
    val tickerMonthlyDataState = viewModel.tickerMonthlyData.collectAsStateWithLifecycle()
    val hasLoadedMonthly = remember(ticker) { mutableStateOf(false) }
    var limit by rememberSaveable {
        mutableIntStateOf(6)
    }
    val choice6 = rememberSaveable {
        mutableStateOf(true)
    }
    val choice12 = rememberSaveable {
        mutableStateOf(false)
    }
    val choice36 = rememberSaveable {
        mutableStateOf(false)
    }

    val isBookmarked by viewModel.isBookmarked(ticker.ticker).collectAsState()

    var showBookMarkModal by rememberSaveable {
        mutableStateOf(false)
    }

    val scope = rememberCoroutineScope()

    val modalSheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)

    DisposableEffect(Unit) {
        viewModel.getTickerInfo(ticker.ticker)
        viewModel.getTickerImage(ticker.ticker)
        setStatusBarLightIcons(true)
        onDispose { }
    }

    BackHandler(true) {
        onBackClick()
    }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            Row(
                modifier = Modifier
                    .padding(horizontal = 8.dp)
                    .statusBarsPadding()
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Start
                ) {
                    IconButton(
                        onClick = {
                            onBackClick()
                        }
                    ) {
                        Icon(
                            modifier = Modifier.size(28.dp),
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
                        if (isBookmarked) {
                            // Already bookmarked → remove directly
                            viewModel.removeBookMark(ticker.ticker)
                        } else {
                            // Not bookmarked → open modal
                            showBookMarkModal = true
                            scope.launch {
                                modalSheetState.show()
                            }
                        }
                    }
                ) {
                    Icon(
                        imageVector = if(isBookmarked) Icons.Filled.Bookmark else Icons.Outlined.BookmarkAdd,
                        contentDescription = "Bookmark",
                        tint = if(isBookmarked) Resources.Colors.ascentGreen else Resources.Colors.textColor
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

                AnimatedContent(targetState = tickerDataState.value) { state ->
                    Column(
                        modifier = Modifier.fillMaxSize(),
                        verticalArrangement = Arrangement.Top,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {


                        when (state) {
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
                                LaunchedEffect(limit) {
                                    hasLoadedMonthly.value = true
                                    viewModel.getMonthlyData(ticker.ticker, limit)
                                }
                                val data = state.data
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.SpaceBetween
                                ) {

                                    val image = tickerImage.value[ticker.ticker]

                                    Row(
                                        verticalAlignment = Alignment.CenterVertically,
                                        horizontalArrangement = Arrangement.Center
                                    ) {
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


                                AnimatedContent(tickerMonthlyDataState.value) { state ->
                                    Column(
                                        modifier = Modifier
                                            .fillMaxWidth(),
                                        verticalArrangement = Arrangement.Top,
                                        horizontalAlignment = Alignment.CenterHorizontally
                                    ) {
                                        when (state) {
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
                                                // or parse date -> month number
                                                val highs = monthLyData.map { it.high }
                                                val lows = monthLyData.map { it.low }

                                                val modelProducer =
                                                    remember { CartesianChartModelProducer() }

                                                LaunchedEffect(monthLyData) {
                                                    modelProducer.runTransaction {
                                                        lineSeries {
                                                            // First line
                                                            series(highs)

                                                            // Second line
                                                            series(lows)
                                                        }
                                                    }
                                                }

                                                CartesianLineChart(
                                                    modifier = Modifier.fillMaxWidth(),
                                                    monthlyData = monthLyData,
                                                    modelProducer = modelProducer
                                                )
                                                HighLowLabel()

                                            }
                                        }
                                    }
                                }

                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth(),
                                    horizontalArrangement = Arrangement.spacedBy(
                                        8.dp,
                                        Alignment.CenterHorizontally
                                    ),
                                    verticalAlignment = Alignment.CenterVertically,
                                ) {

                                    AmountTab(
                                        selected = choice6.value,
                                        onTap = {
                                            choice6.value = true
                                            choice12.value = false
                                            choice36.value = false
                                            limit = 6
                                        },
                                        amount = "6Month"
                                    )

                                    AmountTab(
                                        selected = choice12.value,
                                        onTap = {
                                            choice6.value = false
                                            choice12.value = true
                                            choice36.value = false
                                            limit = 12
                                        },
                                        amount = "1Year"
                                    )

                                    AmountTab(
                                        selected = choice36.value,
                                        onTap = {
                                            choice6.value = false
                                            choice12.value = false
                                            choice36.value = true
                                            limit = 36

                                        },
                                        amount = "3Years"
                                    )
                                }


                                Text(
                                    modifier = Modifier.padding(vertical = 16.dp),
                                    text = data.Description,
                                    color = Resources.Colors.textColor,
                                    fontSize = 24.sp,
                                    fontFamily = Resources.AppFont.dmSans,
                                    lineHeight = 30.sp,
                                )


                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                ) {
                                    Text(
                                        modifier = Modifier,
                                        text = data.Exchange,
                                        fontWeight = FontWeight.Medium,
                                        color = Resources.Colors.ascentGreen,
                                        fontSize = 20.sp,
                                    )

                                    Spacer(
                                        modifier = Modifier.width(16.dp)
                                    )

                                    Text(
                                        modifier = Modifier,
                                        text = data.AssetType,
                                        fontWeight = FontWeight.Medium,
                                        color = Resources.Colors.ascentGreen,
                                        fontSize = 20.sp,
                                    )

                                }



                            }
                        }
                    }

                }


            }

        }
    }

    var watchListName by rememberSaveable { mutableStateOf("") }
    val watchlist by viewModel.getAllWatchlist().collectAsStateWithLifecycle()
    if(showBookMarkModal){
        ModalBottomSheet(
            sheetState = modalSheetState,
            onDismissRequest = {
                scope.launch {
                    modalSheetState.hide()
                    showBookMarkModal = false
                }
            },
            containerColor = Resources.Colors.background
        ) {

            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ){

                OutlinedTextField(
                    modifier = Modifier
                        .weight(7f)
                        .padding(16.dp),
                    value = watchListName,
                    onValueChange = {
                        watchListName = it
                    },
                    shape = RoundedCornerShape(12.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = Resources.Colors.ascentGreen,
                        focusedTextColor = Resources.Colors.textColor,
                        unfocusedTextColor = Resources.Colors.textColor,
                    ),
                    placeholder = {
                        Text(
                            text = "New Watchlist Name",
                            color = Resources.Colors.textColor,
                            fontFamily = Resources.AppFont.dmSans
                        )
                    }
                )

                Button(
                    modifier = Modifier
                        .weight(3f)
                        .padding(end = 16.dp),
                    onClick = {
                        if(watchListName.isNotEmpty()) {
                            viewModel.addWatchList(watchListName)
                        }
                    },
                    shape = RoundedCornerShape(12.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Resources.Colors.ascentGreen
                    )
                ) {

                    Row(
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically
                    ){
                        Text(
                            text = "Add",
                            fontSize = 16.sp,
                            color = Color.White
                        )

                        Icon(
                            imageVector = Icons.Outlined.BookmarkAdd,
                            contentDescription = "Add",
                            tint = Color.White
                        )
                    }

                }

            }

            var selectedId by rememberSaveable { mutableStateOf<Long?>(null) }
            val context = LocalContext.current
            LazyColumn(
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                items(watchlist , key = {it-> it.watchlistId}){item->


                    WatchlistItem(
                        watchlistData = item,
                        isChecked = selectedId == item.watchlistId,
                        onCheckedChange = {
                            selectedId = if (selectedId == item.watchlistId) null else item.watchlistId
                        },
                        onSaveClick = { id ->
                            viewModel.saveToBookmark(watchlistID = id , ticker = ticker)
                            scope.launch {
                                modalSheetState.hide()
                            }
                            Toast.makeText(context, "Saved to ${item.watchlistName}", Toast.LENGTH_SHORT).show()
                        }
                    )



                }
            }
        }
    }

}


@Composable
fun WatchlistItem(
    watchlistData : WatchListData,
    isChecked: Boolean,
    onCheckedChange: () -> Unit,
    onSaveClick: (Long) -> Unit = { _ -> }
){
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp , vertical = 4.dp),
        horizontalArrangement = Arrangement.Start,
        verticalAlignment = Alignment.CenterVertically
    ){


        Row(
            modifier = Modifier
                .weight(7f),
            horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.CenterVertically
        ){
            Checkbox(
                checked = isChecked,
                onCheckedChange = {
                    onCheckedChange()
                }
            )

            Text(
                modifier = Modifier
                    .padding(horizontal = 8.dp),
                text = watchlistData.watchlistName,
                fontFamily = Resources.AppFont.dmSans,
                color = Resources.Colors.textColor,
                fontSize = 18.sp,
            )
        }


        Row(
            modifier = Modifier
                .weight(3f),
            horizontalArrangement = Arrangement.End,
            verticalAlignment = Alignment.CenterVertically
        ){
            AnimatedVisibility(isChecked) {
                Button(
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Resources.Colors.ascentGreen
                    ),
                    onClick = {
                        onSaveClick(watchlistData.watchlistId)
                    }
                ) {
                    Text(
                        text = "Save",
                        fontSize = 16.sp,
                        fontFamily = Resources.AppFont.dmSans,
                        fontWeight = FontWeight.SemiBold
                    )
                }
            }
        }
    }
}



@Composable
fun HighLowLabel() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp, Alignment.CenterHorizontally),
        verticalAlignment = Alignment.CenterVertically
    ) {

        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Box(
                modifier = Modifier
                    .size(16.dp)
                    .background(color = Resources.Colors.ascentGreen)
            )

            Text(
                text = "High",
                color = Resources.Colors.textColor,
                fontFamily = Resources.AppFont.dmSans,
                fontWeight = FontWeight.Normal,
                fontSize = 16.sp
            )

        }
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Box(
                modifier = Modifier
                    .size(16.dp)
                    .background(color = Resources.Colors.ascentRed)
            )

            Text(
                text = "Low",
                color = Resources.Colors.textColor,
                fontFamily = Resources.AppFont.dmSans,
                fontWeight = FontWeight.Normal,
                fontSize = 16.sp
            )

        }
    }
}