package org.soumen.etflux

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import org.koin.androidx.compose.koinViewModel
import org.soumen.etflux.ui.theme.ETFluxTheme
import org.soumen.home.presentation.states.HomeScreenDataState
import org.soumen.home.presentation.viewmodels.HomeViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val homeViewModel = koinViewModel<HomeViewModel>()
            ETFluxTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Greeting(
                        name = "Android",
                        modifier = Modifier.padding(innerPadding),
                        homeViewModel = homeViewModel
                    )
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier , homeViewModel : HomeViewModel) {
    val state = homeViewModel.homeScreenDataState.collectAsStateWithLifecycle()
    LaunchedEffect(Unit) {
        homeViewModel.getGainersAndLosersData()
    }
    when(val s = state.value){
        is HomeScreenDataState.Error -> {

        }
        HomeScreenDataState.Loading -> {
            LinearProgressIndicator()
        }
        is HomeScreenDataState.Success -> {
            val data = s.data

            val d = data.topGainers ?: emptyList()
            LazyColumn {
                items(d.take(4)) { item ->
                    Text(text = item.ticker, modifier = modifier, fontSize = 45.sp)
                }
            }

        }
    }
}

