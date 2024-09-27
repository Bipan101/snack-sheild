package com.example.snackshield.navigation.navgraph

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.example.snackshield.feature_auth.presentation.AuthViewModel
import com.example.snackshield.feature_auth.presentation.screen.DetailScreen
import com.example.snackshield.feature_auth.presentation.screen.SignInScreen
import com.example.snackshield.feature_auth.presentation.screen.SignUpScreen
import com.example.snackshield.navigation.elements.navigateWithSingleTop

fun NavGraphBuilder.authNavGraph(navController: NavController, authViewModel: AuthViewModel) {
    navigation(startDestination = AuthNavGraph.SIGNIN_SCREEN, route = AuthNavGraph.AUTH_ROUTE) {
        composable(
            route = AuthNavGraph.SIGNIN_SCREEN,
            enterTransition = { slideIntoContainer(AnimatedContentTransitionScope.SlideDirection.Right) },
            popEnterTransition = { slideIntoContainer(AnimatedContentTransitionScope.SlideDirection.Right) },
            exitTransition = { slideOutOfContainer(AnimatedContentTransitionScope.SlideDirection.Left) },
            popExitTransition = { slideOutOfContainer(AnimatedContentTransitionScope.SlideDirection.Left) },
        ) {
            SignInScreen(
                toSignUp = { navController.navigateWithSingleTop(AuthNavGraph.SIGNUP_SCREEN) },
                toHome = { navController.navigateWithSingleTop(HomeNavGraph.HOME_ROUTE) },
                authViewModel
            )
        }
        composable(
            route = AuthNavGraph.SIGNUP_SCREEN,
            enterTransition = { slideIntoContainer(AnimatedContentTransitionScope.SlideDirection.Left) },
            popEnterTransition = { slideIntoContainer(AnimatedContentTransitionScope.SlideDirection.Left) },
            exitTransition = { slideOutOfContainer(AnimatedContentTransitionScope.SlideDirection.Right) },
            popExitTransition = { slideOutOfContainer(AnimatedContentTransitionScope.SlideDirection.Right) },
        ) {
            SignUpScreen(
                goBack = { navController.popBackStack() },
                toDetail = { navController.navigateWithSingleTop(AuthNavGraph.ALLERGY_SCREEN) },
                authViewModel
            )
        }
        composable(
            route = AuthNavGraph.ALLERGY_SCREEN,
            enterTransition = { slideIntoContainer(AnimatedContentTransitionScope.SlideDirection.Left) },
            popEnterTransition = { slideIntoContainer(AnimatedContentTransitionScope.SlideDirection.Left) },
            exitTransition = { slideOutOfContainer(AnimatedContentTransitionScope.SlideDirection.Right) },
            popExitTransition = { slideOutOfContainer(AnimatedContentTransitionScope.SlideDirection.Right) },
        ) {
            DetailScreen(
                toHome = { navController.navigateWithSingleTop(HomeNavGraph.HOME_ROUTE) },
                authViewModel
            )
        }
    }
}

object AuthNavGraph {
    const val AUTH_ROUTE = "auth_route"
    const val SIGNIN_SCREEN = "signin_screen"
    const val SIGNUP_SCREEN = "signup_screen"
    const val ALLERGY_SCREEN = "allergy_screen"
}