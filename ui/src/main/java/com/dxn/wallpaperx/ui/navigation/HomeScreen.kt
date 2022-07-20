package com.dxn.wallpaperx.ui.navigation

import androidx.annotation.DrawableRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.*
import androidx.compose.ui.graphics.vector.ImageVector
import com.dxn.wallpaperx.ui.R


sealed class HomeScreen(
    val title: String,
    val route: String,
    @DrawableRes val resId: Int,
    val icon: ImageVector
) {
    object Wallpapers : HomeScreen(
        "Wallpapers",
        "route_wallpapers",
        R.drawable.ic_wallpapers,
        Icons.Rounded.Wallpaper
    )

    object Collections : HomeScreen(
        "Collections",
        "route_collections",
        R.drawable.ic_collections,
        Icons.Rounded.List
    )

    object Favourites : HomeScreen(
        "Favourites",
        "route_favourites",
        R.drawable.ic_favourites,
        Icons.Rounded.FavoriteBorder
    )

    object Setting :
        HomeScreen("Settings", "route_settings", R.drawable.ic_settings, Icons.Rounded.Settings)
}