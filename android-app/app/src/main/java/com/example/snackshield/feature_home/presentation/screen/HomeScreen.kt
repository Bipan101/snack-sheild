package com.example.snackshield.feature_home.presentation.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import com.example.snackshield.R
import com.example.snackshield.feature_home.presentation.components.BottomBar
import com.example.snackshield.feature_home.presentation.components.SearchBoxTopBar

@Composable
fun HomeScreen(toSearch: () -> Unit, toProfile: () -> Unit, toScan: () -> Unit) {
    Scaffold(
        bottomBar = {
            BottomBar(toScan)
        },
        topBar = {

            SearchBoxTopBar(
                toSearch = toSearch,
                toProfile = toProfile,
            )

        }
    ) { paddingValues: PaddingValues ->
        Column(
            modifier = Modifier
                .padding(top = paddingValues.calculateTopPadding().times(0))
        ) {
            HomeView()
        }
    }
}

@Composable
fun HomeView(modifier: Modifier = Modifier) {
    LazyColumn(
        modifier = modifier
            .fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        item {
            Image(
                painter = painterResource(R.drawable.nothing_here),
                contentDescription = "nothing_here"
            )
            Text(
                "Your all activity will appear here",
                style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.Normal),
                textAlign = TextAlign.Center
            )
        }
    }
}
