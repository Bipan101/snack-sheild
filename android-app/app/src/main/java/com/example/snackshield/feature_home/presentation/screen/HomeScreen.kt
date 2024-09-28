package com.example.snackshield.feature_home.presentation.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.BarChart
import androidx.compose.material.icons.filled.DocumentScanner
import androidx.compose.material.icons.filled.Recommend
import androidx.compose.material.icons.filled.SettingsOverscan
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.snackshield.R
import com.example.snackshield.feature_home.presentation.components.SearchBoxTopBar

@Composable
fun HomeScreen(
    toSearch: () -> Unit, toProfile: () -> Unit, toBarcode: () -> Unit,
    toLabel: () -> Unit, toFood: () -> Unit, toRecommend: () -> Unit
) {
    Scaffold(
        topBar = {
            SearchBoxTopBar(
                toSearch = toSearch,
                toProfile = toProfile,
            )
        }
    ) { paddingValues: PaddingValues ->
        Column(
            modifier = Modifier
                .padding(top = paddingValues.calculateTopPadding())
        ) {
            HomeView(toBarcode, toLabel, toFood, toRecommend)
        }
    }
}

@Composable
fun HomeView(
    toBarcode: () -> Unit,
    toLabel: () -> Unit,
    toFood: () -> Unit,
    toRecommend: () -> Unit
) {
    HomeOptions(toBarcode, toLabel, toFood, toRecommend)
    HorizontalDivider()
    HomeRecentData()
}

@Composable
fun HomeOptions(
    toBarcode: () -> Unit,
    toLabel: () -> Unit,
    toFood: () -> Unit,
    toRecommend: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(12.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        HomeOption(text = "Scan Label", icon = Icons.Default.SettingsOverscan) {
            toLabel()
        }
        HomeOption(text = "Scan Barcode", icon = Icons.Default.BarChart) {
            toBarcode()
        }
        HomeOption(text = "Scan Food", icon = Icons.Default.DocumentScanner) {
            toFood()
        }
        HomeOption(text = "Recommend", icon = Icons.Default.Recommend) {
            toRecommend()
        }
    }
}

@Composable
fun HomeOption(text: String, icon: ImageVector, onClick: () -> Unit) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Column(
            modifier = Modifier
                .padding(4.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            IconButton(
                onClick = {
                    onClick()
                }, modifier = Modifier
                    .size(60.dp)
            ) {
                Icon(
                    imageVector = icon,
                    contentDescription = text,
                    modifier = Modifier.size(40.dp)
                )
            }
            Text(text = text, style = MaterialTheme.typography.labelSmall)
        }

    }
}

@Composable
fun HomeRecentData(modifier: Modifier = Modifier) {
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