package org.soumen.etflux.presentation.presentation

import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import kotlinx.serialization.Serializable
import org.soumen.home.presentation.bottomBar.BottomBarItem
import org.soumen.home.presentation.screens.AllLosersScreen
import org.soumen.home.presentation.screens.GainerScreen
import org.soumen.home.presentation.screens.HomeScreen
import org.soumen.home.presentation.viewmodels.HomeViewModel


@Serializable
object AllGainersScreenRoute

@Serializable
object AllLosersScreenRoute

@Composable
fun Navigation(
    homeViewModel: HomeViewModel
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

            HomeScreen(
                bottomBarItems = bottomBar,
                homeViewModel = homeViewModel,
                currentRoute = currentRoute,
                onBottomBarClick = { route ->
                    navController.navigate(route) {

                    }
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

                }
            )
        }

    }
}