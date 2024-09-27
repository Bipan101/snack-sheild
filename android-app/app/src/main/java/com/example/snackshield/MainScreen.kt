package com.example.snackshield

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.example.snackshield.common.domain.repo.SessionManager
import com.example.snackshield.feature_auth.presentation.AuthViewModel
import com.example.snackshield.navigation.navgraph.AuthNavGraph
import com.example.snackshield.navigation.navgraph.HomeNavGraph
import com.example.snackshield.navigation.navgraph.authNavGraph
import com.example.snackshield.navigation.navgraph.homeNavGraph

@Composable
fun MainScreen(sessionManager: SessionManager) {
    val navController = rememberNavController()
    val authViewModel: AuthViewModel = hiltViewModel()
    Scaffold(
        modifier = Modifier
            .fillMaxSize(),
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(top = padding.calculateTopPadding())
        ) {
            NavHost(
                navController = navController,
                startDestination = getNavDestination(sessionManager)
            ) {
                authNavGraph(navController, authViewModel)
                homeNavGraph(navController)
            }
        }
    }
}

fun getNavDestination(sessionManager: SessionManager): String {
    return HomeNavGraph.HOME_ROUTE
//    return if (sessionManager.getUser() != null && sessionManager.getUser()!!.token.isNotEmpty()) {
//        HomeNavGraph.HOME_ROUTE
//    } else {
//        AuthNavGraph.AUTH_ROUTE
//    }
}