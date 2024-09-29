package com.example.snackshield.feature_home.presentation.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.snackshield.R
import com.example.snackshield.common.components.Spacing
import com.example.snackshield.feature_home.presentation.components.SearchBoxTopBar
import com.example.snackshield.feature_scan.presentation.ScanViewModel

@Composable
fun HomeScreen(
    viewModel: ScanViewModel,
    toSearch: () -> Unit, toProfile: () -> Unit, toBarcode: () -> Unit,
    toLabel: () -> Unit, toFood: () -> Unit, toRecommend: () -> Unit
) {
    LaunchedEffect(true) {
        viewModel.resetState()
    }
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
            HomeRecentData()
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
            .fillMaxSize()
            .padding(12.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        items(recentData) { data ->
            DataView(data)
            Spacing(12)
        }
    }
}

@Composable
fun DataView(data: RecentData) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(12.dp))
            .background(MaterialTheme.colorScheme.surfaceContainer)
            .padding(8.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        AsyncImage(
            model = data.image,
            contentDescription = "image",
            modifier = Modifier
                .padding(4.dp)
                .size(40.dp)
                .clip(RoundedCornerShape(12.dp)),
            contentScale = ContentScale.Crop,
        )
        Spacing(width = 12)
        Column() {
            Text(
                data.text, style = MaterialTheme.typography.bodyLarge.copy(
                    fontWeight = FontWeight.Bold,
                )
            )
            Spacing(4)
            when (data.status) {
                true -> Text("This is safe for you to consume")
                false -> Text("This is potentially unsafe for you to consume")
            }
        }
    }
}

data class RecentData(
    val text: String,
    val image: Int,
    val status: Boolean
)

val recentData = listOf(
    RecentData("Lasagna", R.drawable.img, false),
    RecentData("Burger", R.drawable.img_1, true),
    RecentData("Spaghetti", R.drawable.img_2, false),
    RecentData("Sandwich", R.drawable.img_3, true),
    RecentData("Burger and Fries", R.drawable.img_4, false),
    RecentData("Nugget", R.drawable.img_5, false),
    RecentData("Pasta", R.drawable.img_6, true),
    RecentData("Mushroom", R.drawable.img_7, false),
    RecentData("Pizza", R.drawable.img_8, true),
    )