package org.soumen.home.presentation.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import org.soumen.core.utils.setStatusBarLightIcons
import org.soumen.home.presentation.bottomBar.BottomBar
import org.soumen.home.presentation.bottomBar.BottomBarItem
import org.soumen.home.presentation.viewmodels.HomeViewModel
import org.soumen.shared.domain.Resources

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    bottomBarItems : List<BottomBarItem>,
    homeViewModel: HomeViewModel,
    onBottomBarClick : (String) -> Unit = {},
    currentRoute : String?

) {
    DisposableEffect(Unit) {
        setStatusBarLightIcons(true)
        onDispose {

        }
    }
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        bottomBar = {
            BottomBar(
                bottomBarItems,
                onBottomBarClick = onBottomBarClick,
                currentRoute = currentRoute
            )
        }
    ) {innerPadding->
        Column(
            modifier = modifier
                .fillMaxSize()
                .background(color = Resources.Colors.background)
        ) {
            Column(
                modifier = Modifier.padding(innerPadding)
            ) {



            }




        }

    }
}