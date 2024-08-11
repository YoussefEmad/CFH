package com.example.cfh.domain.models

sealed class NavigationItem(val route:String) {


    object Home : NavigationItem("Home")
    object DetailsScreen: NavigationItem("Details")


}