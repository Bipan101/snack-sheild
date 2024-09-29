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
import com.example.snackshield.feature_scan.presentation.screen.RecommendScreen
import com.example.snackshield.feature_scan.presentation.ScanViewModel
import com.example.snackshield.feature_scan.presentation.responses.RecommendResponseScreen
import com.example.snackshield.feature_scan.presentation.responses.ScanBarcodeResponseScreen
import com.example.snackshield.feature_scan.presentation.responses.ScanImageResponseScreen
import com.example.snackshield.feature_scan.presentation.responses.ScanTextResponseScreen
import com.example.snackshield.feature_scan.presentation.screen.ScanBarcodeScreen
import com.example.snackshield.feature_scan.presentation.screen.ScanFoodScreen
import com.example.snackshield.feature_scan.presentation.screen.ScanTextScreen
import com.example.snackshield.navigation.elements.navigateWithSingleTop

fun NavGraphBuilder.homeNavGraph(navController: NavController,scanViewModel: ScanViewModel) {
    navigation(startDestination = HomeNavGraph.HOME_SCREEN, route = HomeNavGraph.HOME_ROUTE) {
        composable(
            route = HomeNavGraph.HOME_SCREEN,
            enterTransition = {
                when (initialState.destination.route) {
                    HomeNavGraph.FOOD_SCAN_SCREEN -> slideIntoContainer(
                        AnimatedContentTransitionScope.SlideDirection.Right
                    )

                    HomeNavGraph.TEXT_SCAN_SCREEN -> slideIntoContainer(
                        AnimatedContentTransitionScope.SlideDirection.Right
                    )

                    HomeNavGraph.BARCODE_SCAN_SCREEN -> slideIntoContainer(
                        AnimatedContentTransitionScope.SlideDirection.Right
                    )

                    HomeNavGraph.RECOMMEND_SCREEN -> slideIntoContainer(
                        AnimatedContentTransitionScope.SlideDirection.Right
                    )

                    HomeNavGraph.HOME_SEARCH_SCREEN -> slideIntoContainer(
                        AnimatedContentTransitionScope.SlideDirection.Up
                    )

                    else -> fadeIn(animationSpec = tween(200, easing = FastOutLinearInEasing))
                }
            },
            popEnterTransition = {
                when (initialState.destination.route) {
                    HomeNavGraph.FOOD_SCAN_SCREEN -> slideIntoContainer(
                        AnimatedContentTransitionScope.SlideDirection.Right
                    )

                    HomeNavGraph.TEXT_SCAN_SCREEN -> slideIntoContainer(
                        AnimatedContentTransitionScope.SlideDirection.Right
                    )

                    HomeNavGraph.BARCODE_SCAN_SCREEN -> slideIntoContainer(
                        AnimatedContentTransitionScope.SlideDirection.Right
                    )

                    HomeNavGraph.RECOMMEND_SCREEN -> slideIntoContainer(
                        AnimatedContentTransitionScope.SlideDirection.Right
                    )

                    HomeNavGraph.HOME_SEARCH_SCREEN -> slideIntoContainer(
                        AnimatedContentTransitionScope.SlideDirection.Up
                    )

                    else -> fadeIn(animationSpec = tween(200, easing = FastOutLinearInEasing))
                }
            },
            exitTransition = {
                when (targetState.destination.route) {
                    HomeNavGraph.FOOD_SCAN_SCREEN -> slideOutOfContainer(
                        AnimatedContentTransitionScope.SlideDirection.Left
                    )

                    HomeNavGraph.TEXT_SCAN_SCREEN -> slideOutOfContainer(
                        AnimatedContentTransitionScope.SlideDirection.Left
                    )

                    HomeNavGraph.BARCODE_SCAN_SCREEN -> slideOutOfContainer(
                        AnimatedContentTransitionScope.SlideDirection.Left
                    )

                    HomeNavGraph.RECOMMEND_SCREEN -> slideOutOfContainer(
                        AnimatedContentTransitionScope.SlideDirection.Left
                    )

                    HomeNavGraph.HOME_SEARCH_SCREEN -> slideOutOfContainer(
                        AnimatedContentTransitionScope.SlideDirection.Down
                    )

                    else -> fadeOut(animationSpec = tween(200, easing = FastOutLinearInEasing))
                }
            },
            popExitTransition = {
                when (targetState.destination.route) {
                    HomeNavGraph.FOOD_SCAN_SCREEN -> slideOutOfContainer(
                        AnimatedContentTransitionScope.SlideDirection.Left
                    )

                    HomeNavGraph.TEXT_SCAN_SCREEN -> slideOutOfContainer(
                        AnimatedContentTransitionScope.SlideDirection.Left
                    )

                    HomeNavGraph.BARCODE_SCAN_SCREEN -> slideOutOfContainer(
                        AnimatedContentTransitionScope.SlideDirection.Left
                    )

                    HomeNavGraph.RECOMMEND_SCREEN -> slideOutOfContainer(
                        AnimatedContentTransitionScope.SlideDirection.Left
                    )

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
                toBarcode = { navController.navigateWithSingleTop(HomeNavGraph.BARCODE_SCAN_SCREEN) },
                toLabel = { navController.navigateWithSingleTop(HomeNavGraph.TEXT_SCAN_SCREEN) },
                toFood = { navController.navigateWithSingleTop(HomeNavGraph.FOOD_SCAN_SCREEN) },
                toRecommend = {
                    navController.navigateWithSingleTop(HomeNavGraph.RECOMMEND_SCREEN)
                }
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
            route = HomeNavGraph.BARCODE_SCAN_SCREEN,
            enterTransition = { slideIntoContainer(AnimatedContentTransitionScope.SlideDirection.Left) },
            popEnterTransition = { slideIntoContainer(AnimatedContentTransitionScope.SlideDirection.Left) },
            exitTransition = { slideOutOfContainer(AnimatedContentTransitionScope.SlideDirection.Right) },
            popExitTransition = { slideOutOfContainer(AnimatedContentTransitionScope.SlideDirection.Right) },
        ) {
            ScanBarcodeScreen(scanViewModel, goBack = navController::navigateUp,toResponse = {
                navController.navigateWithSingleTop(HomeNavGraph.BARCODE_SCAN_RESPONSE_SCREEN)
            })
        }
        composable(
            route = HomeNavGraph.TEXT_SCAN_SCREEN,
            enterTransition = { slideIntoContainer(AnimatedContentTransitionScope.SlideDirection.Left) },
            popEnterTransition = { slideIntoContainer(AnimatedContentTransitionScope.SlideDirection.Left) },
            exitTransition = { slideOutOfContainer(AnimatedContentTransitionScope.SlideDirection.Right) },
            popExitTransition = { slideOutOfContainer(AnimatedContentTransitionScope.SlideDirection.Right) },
        ) {
            ScanTextScreen(scanViewModel, goBack = navController::navigateUp,toResponse = {
                navController.navigateWithSingleTop(HomeNavGraph.TEXT_SCAN_RESPONSE_SCREEN)
            })
        }
        composable(
            route = HomeNavGraph.FOOD_SCAN_SCREEN,
            enterTransition = { slideIntoContainer(AnimatedContentTransitionScope.SlideDirection.Left) },
            popEnterTransition = { slideIntoContainer(AnimatedContentTransitionScope.SlideDirection.Left) },
            exitTransition = { slideOutOfContainer(AnimatedContentTransitionScope.SlideDirection.Right) },
            popExitTransition = { slideOutOfContainer(AnimatedContentTransitionScope.SlideDirection.Right) },
        ) {
            ScanFoodScreen(scanViewModel, goBack = navController::navigateUp,toResponse = {
                navController.navigateWithSingleTop(HomeNavGraph.FOOD_SCAN_RESPONSE_SCREEN)
            })
        }
        composable(
            route = HomeNavGraph.RECOMMEND_SCREEN,
            enterTransition = { slideIntoContainer(AnimatedContentTransitionScope.SlideDirection.Left) },
            popEnterTransition = { slideIntoContainer(AnimatedContentTransitionScope.SlideDirection.Left) },
            exitTransition = { slideOutOfContainer(AnimatedContentTransitionScope.SlideDirection.Right) },
            popExitTransition = { slideOutOfContainer(AnimatedContentTransitionScope.SlideDirection.Right) },
        ) {
            RecommendScreen(scanViewModel, goBack = navController::navigateUp,toResponse = {
                navController.navigateWithSingleTop(HomeNavGraph.RECOMMEND_RESPONSE_SCREEN)
            })
        }
        composable(
            route = HomeNavGraph.RECOMMEND_RESPONSE_SCREEN,
            enterTransition = { slideIntoContainer(AnimatedContentTransitionScope.SlideDirection.Left) },
            popEnterTransition = { slideIntoContainer(AnimatedContentTransitionScope.SlideDirection.Left) },
            exitTransition = { slideOutOfContainer(AnimatedContentTransitionScope.SlideDirection.Right) },
            popExitTransition = { slideOutOfContainer(AnimatedContentTransitionScope.SlideDirection.Right) },
        ) {
            RecommendResponseScreen(scanViewModel, goBack = { navController.navigateWithSingleTop(HomeNavGraph.HOME_SCREEN) })
        }
        composable(
            route = HomeNavGraph.TEXT_SCAN_RESPONSE_SCREEN,
            enterTransition = { slideIntoContainer(AnimatedContentTransitionScope.SlideDirection.Left) },
            popEnterTransition = { slideIntoContainer(AnimatedContentTransitionScope.SlideDirection.Left) },
            exitTransition = { slideOutOfContainer(AnimatedContentTransitionScope.SlideDirection.Right) },
            popExitTransition = { slideOutOfContainer(AnimatedContentTransitionScope.SlideDirection.Right) },
        ) {
            ScanTextResponseScreen(scanViewModel, goBack = { navController.navigateWithSingleTop(HomeNavGraph.HOME_SCREEN) })
        }
        composable(
            route = HomeNavGraph.BARCODE_SCAN_RESPONSE_SCREEN,
            enterTransition = { slideIntoContainer(AnimatedContentTransitionScope.SlideDirection.Left) },
            popEnterTransition = { slideIntoContainer(AnimatedContentTransitionScope.SlideDirection.Left) },
            exitTransition = { slideOutOfContainer(AnimatedContentTransitionScope.SlideDirection.Right) },
            popExitTransition = { slideOutOfContainer(AnimatedContentTransitionScope.SlideDirection.Right) },
        ) {
            ScanBarcodeResponseScreen(scanViewModel, goBack = { navController.navigateWithSingleTop(HomeNavGraph.HOME_SCREEN) })
        }
        composable(
            route = HomeNavGraph.FOOD_SCAN_RESPONSE_SCREEN,
            enterTransition = { slideIntoContainer(AnimatedContentTransitionScope.SlideDirection.Left) },
            popEnterTransition = { slideIntoContainer(AnimatedContentTransitionScope.SlideDirection.Left) },
            exitTransition = { slideOutOfContainer(AnimatedContentTransitionScope.SlideDirection.Right) },
            popExitTransition = { slideOutOfContainer(AnimatedContentTransitionScope.SlideDirection.Right) },
        ) {
            ScanImageResponseScreen(scanViewModel, goBack = { navController.navigateWithSingleTop(HomeNavGraph.HOME_SCREEN) })
        }
    }
}

object HomeNavGraph {
    const val HOME_ROUTE = "home_route"
    const val HOME_SCREEN = "home_screen"
    const val HOME_SEARCH_SCREEN = "home_search_screen"
    const val BARCODE_SCAN_SCREEN = "barcode_scan_screen"
    const val TEXT_SCAN_SCREEN = "text_scan_screen"
    const val FOOD_SCAN_SCREEN = "food_scan_screen"
    const val RECOMMEND_SCREEN = "recommend_screen"
    const val BARCODE_SCAN_RESPONSE_SCREEN = "barcode_scan_response_screen"
    const val TEXT_SCAN_RESPONSE_SCREEN = "text_scan_response_screen"
    const val FOOD_SCAN_RESPONSE_SCREEN = "food_scan_response_screen"
    const val RECOMMEND_RESPONSE_SCREEN = "recommend_response_screen"

}