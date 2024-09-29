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
import com.example.snackshield.feature_scan.presentation.ScanViewModel
import com.example.snackshield.navigation.navgraph.AuthNavGraph
import com.example.snackshield.navigation.navgraph.HomeNavGraph
import com.example.snackshield.navigation.navgraph.authNavGraph
import com.example.snackshield.navigation.navgraph.homeNavGraph

@Composable
fun MainScreen(sessionManager: SessionManager) {
    val navController = rememberNavController()
    val authViewModel: AuthViewModel = hiltViewModel()
    val scanViewModel : ScanViewModel = hiltViewModel()
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
                authNavGraph(navController)
                homeNavGraph(navController,scanViewModel)
            }
        }
    }
}

fun getNavDestination(sessionManager: SessionManager): String {
    return if (sessionManager.getUser() != null && sessionManager.getUser()!!.id.isNotEmpty()) {
        HomeNavGraph.HOME_ROUTE
    } else {
        AuthNavGraph.AUTH_ROUTE
    }
}