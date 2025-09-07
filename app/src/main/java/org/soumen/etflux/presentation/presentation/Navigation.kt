package org.soumen.etflux.presentation.presentation

import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import org.soumen.home.domain.dataModels.Data
import org.soumen.shared.presentation.bottomBar.BottomBarItem
import org.soumen.home.presentation.screens.AllLosersScreen
import org.soumen.home.presentation.screens.GainerScreen
import org.soumen.home.presentation.screens.HomeScreen
import org.soumen.home.presentation.screens.TickerInfoScreen
import org.soumen.home.presentation.viewmodels.HomeViewModel
import org.soumen.watchlist.presentation.WatchListItemsScreen
import org.soumen.watchlist.presentation.WatchlistScreen
import org.soumen.watchlist.presentation.viewmodel.WatchlistViewModel


@Serializable
object AllGainersScreenRoute

@Serializable
object AllLosersScreenRoute

@Serializable
data class TickerInfoRoute(val tickerJson: String)

@Serializable
data class IndividualWatchlistScreenRoute(val watchlistID : Long)

@Composable
fun Navigation(
    homeViewModel: HomeViewModel,
    watchListViewModel: WatchlistViewModel
){

    val navController = rememberNavController()
    val bottomBar = listOf(
        BottomBarItem.Home,
        BottomBarItem.WatchList
    )

    NavHost(
        navController = navController,
        startDestination = BottomBarItem.Home.route
    ) {

        composable(
            route = BottomBarItem.Home.route,
            enterTransition = { fadeIn(animationSpec = tween(100)) },
            exitTransition = { fadeOut(animationSpec = tween(200)) }
        ){

            val navBackStackEntry by navController.currentBackStackEntryAsState()
            val currentRoute = navBackStackEntry?.destination?.route

            HomeScreen(
                bottomBarItems = bottomBar,
                homeViewModel = homeViewModel,
                currentRoute = currentRoute,
                onBottomBarClick = { route ->
                    navController.navigate(route) {

                    }
                },
                onSearchClick = {

                },
                onItemClick = {
                    val route = TickerInfoRoute(Json.encodeToString(it))
                    navController.navigate(route)
                },
                onGainersViewAllClick = {
                    navController.navigate(AllGainersScreenRoute)
                },
                onLosersViewAllClick = {
                    navController.navigate(AllLosersScreenRoute)
                }
            )
        }


        composable(
            route = BottomBarItem.WatchList.route,
            enterTransition = { fadeIn(animationSpec = tween(100)) },
            exitTransition = { fadeOut(animationSpec = tween(200)) }
        ) {

            val navBackStackEntry by navController.currentBackStackEntryAsState()
            val currentRoute = navBackStackEntry?.destination?.route

            WatchlistScreen(
                watchlistViewModel = watchListViewModel,
                onBackClick = {
                    navController.popBackStack()
                },
                bottomBarItems = bottomBar,
                modifier = Modifier,
                onBottomBarClick = { route ->
                    navController.navigate(route) {

                    }
                },
                currentRoute = currentRoute,
                onItemClick = {
                    navController.navigate(IndividualWatchlistScreenRoute(it))
                }
            )

        }

        composable<AllGainersScreenRoute>(
            enterTransition = {
                slideInHorizontally(
                    initialOffsetX = { fullWidth -> fullWidth }, // start just outside the right edge
                    animationSpec = tween(500)
                )
            },
            exitTransition = {
                slideOutHorizontally(
                    targetOffsetX = { fullWidth -> fullWidth }, // exit towards right edge
                    animationSpec = tween(500)
                )
            }
        ) {
            GainerScreen(
                homeViewModel = homeViewModel,
                onBackClick = {
                    navController.popBackStack()
                },
                onItemClick = {
                    val route = TickerInfoRoute(Json.encodeToString(it))
                    navController.navigate(route)
                }
            )
        }


        composable<AllLosersScreenRoute>(
            enterTransition = {
                slideInHorizontally(
                    initialOffsetX = { fullWidth -> fullWidth }, // start just outside the right edge
                    animationSpec = tween(500)
                )
            },
            exitTransition = {
                slideOutHorizontally(
                    targetOffsetX = { fullWidth -> fullWidth }, // exit towards right edge
                    animationSpec = tween(500)
                )
            }
        ) {
            AllLosersScreen(
                homeViewModel = homeViewModel,
                onBackClick = {
                    navController.popBackStack()
                },
                onItemClick = {
                    val route = TickerInfoRoute(Json.encodeToString(it))
                    navController.navigate(route)
                }
            )
        }

        composable<TickerInfoRoute>(
            enterTransition = { fadeIn(animationSpec = tween(100)) },
            exitTransition = { fadeOut(animationSpec = tween(200)) }
        ) {
            val json = it.toRoute<TickerInfoRoute>().tickerJson
            val data = Json.decodeFromString<Data>(json)

            TickerInfoScreen(
                modifier = Modifier,
                viewModel = homeViewModel,
                ticker = data,
                onBackClick = {
                    navController.popBackStack()
                }
            )

        }


        composable<IndividualWatchlistScreenRoute>(
            enterTransition = { fadeIn(animationSpec = tween(100)) },
            exitTransition = { fadeOut(animationSpec = tween(200)) }
        ) {
            val watchlistID = it.toRoute<IndividualWatchlistScreenRoute>().watchlistID

            WatchListItemsScreen(
                watchListId = watchlistID,
                watchlistViewModel = watchListViewModel,
                homeViewModel = homeViewModel,
                onBackClick = {
                    navController.popBackStack()
                },
                onItemClick = {
                    val route = TickerInfoRoute(Json.encodeToString(it))
                    navController.navigate(route)
                }
            )

        }



    }
}