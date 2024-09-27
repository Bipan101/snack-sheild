package com.example.snackshield.navigation.elements

import androidx.navigation.NavController

fun NavController.navigateWithSingleTop(route: String) {
    this.navigate(route) {
       launchSingleTop = true
       restoreState = true
   }
}