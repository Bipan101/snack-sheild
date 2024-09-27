package com.example.snackshield.navigation.navgraph

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.FastOutLinearInEasing
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.example.snackshield.feature_home.presentation.screen.HomeScreen
import com.example.snackshield.feature_home.presentation.screen.SearchScreen
import com.example.snackshield.feature_scan.presentation.ScanViewModel
import com.example.snackshield.feature_scan.presentation.screen.ScanScreen
import com.example.snackshield.navigation.elements.navigateWithSingleTop

fun NavGraphBuilder.homeNavGraph(navController: NavController) {
    navigation(startDestination = HomeNavGraph.HOME_SCREEN, route = HomeNavGraph.HOME_ROUTE) {
        composable(
            route = HomeNavGraph.HOME_SCREEN,
            enterTransition = {
                when (initialState.destination.route) {
                    HomeNavGraph.SCAN_SCREEN -> slideIntoContainer(AnimatedContentTransitionScope.SlideDirection.Right)
                    HomeNavGraph.HOME_SEARCH_SCREEN -> slideIntoContainer(
                        AnimatedContentTransitionScope.SlideDirection.Up
                    )

                    else -> fadeIn(animationSpec = tween(200, easing = FastOutLinearInEasing))
                }
            },
            popEnterTransition = {
                when (initialState.destination.route) {
                    HomeNavGraph.SCAN_SCREEN -> slideIntoContainer(AnimatedContentTransitionScope.SlideDirection.Right)
                    HomeNavGraph.HOME_SEARCH_SCREEN -> slideIntoContainer(
                        AnimatedContentTransitionScope.SlideDirection.Up
                    )

                    else -> fadeIn(animationSpec = tween(200, easing = FastOutLinearInEasing))
                }
            },
            exitTransition = {
                when (targetState.destination.route) {
                    HomeNavGraph.SCAN_SCREEN -> slideOutOfContainer(AnimatedContentTransitionScope.SlideDirection.Left)
                    HomeNavGraph.HOME_SEARCH_SCREEN -> slideOutOfContainer(
                        AnimatedContentTransitionScope.SlideDirection.Down
                    )

                    else -> fadeOut(animationSpec = tween(200, easing = FastOutLinearInEasing))
                }
            },
            popExitTransition = {
                when (targetState.destination.route) {
                    HomeNavGraph.SCAN_SCREEN -> slideOutOfContainer(AnimatedContentTransitionScope.SlideDirection.Left)
                    HomeNavGraph.HOME_SEARCH_SCREEN -> slideOutOfContainer(
                        AnimatedContentTransitionScope.SlideDirection.Down
                    )

                    else -> fadeOut(animationSpec = tween(200, easing = FastOutLinearInEasing))
                }
            },
        ) {
            HomeScreen(
                toSearch = { navController.navigateWithSingleTop(HomeNavGraph.HOME_SEARCH_SCREEN) },
                toProfile = {},
                toScan = { navController.navigateWithSingleTop(HomeNavGraph.SCAN_SCREEN) }
            )
        }
        composable(
            route = HomeNavGraph.HOME_SEARCH_SCREEN,
            enterTransition = {
                slideIntoContainer(
                    AnimatedContentTransitionScope.SlideDirection.Down,
                )
            },
            popEnterTransition = {
                slideIntoContainer(
                    AnimatedContentTransitionScope.SlideDirection.Down,
                )
            },
            exitTransition = {
                slideOutOfContainer(
                    AnimatedContentTransitionScope.SlideDirection.Up,
                )
            },
            popExitTransition = {
                slideOutOfContainer(
                    AnimatedContentTransitionScope.SlideDirection.Up,
                )
            },
        ) {
            SearchScreen(goBack = navController::navigateUp)
        }
        composable(
            route = HomeNavGraph.SCAN_SCREEN,
            enterTransition = { slideIntoContainer(AnimatedContentTransitionScope.SlideDirection.Left) },
            popEnterTransition = { slideIntoContainer(AnimatedContentTransitionScope.SlideDirection.Left) },
            exitTransition = { slideOutOfContainer(AnimatedContentTransitionScope.SlideDirection.Right) },
            popExitTransition = { slideOutOfContainer(AnimatedContentTransitionScope.SlideDirection.Right) },
        ) {
            val scanViewModel = ScanViewModel()
            ScanScreen(scanViewModel, goBack = navController::navigateUp)
        }
    }
}

object HomeNavGraph {
    const val HOME_ROUTE = "home_route"
    const val HOME_SCREEN = "home_screen"
    const val HOME_SEARCH_SCREEN = "home_search_screen"
    const val SCAN_SCREEN = "scan_screen"
}