package com.dxn.wallpaperx.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Surface
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import coil.annotation.ExperimentalCoilApi
import com.dxn.wallpaperx.ui.screens.App
import com.dxn.wallpaperx.ui.screens.WallpaperXApp
import com.dxn.wallpaperx.ui.theme.WallpaperXTheme
import com.google.accompanist.insets.ProvideWindowInsets
import com.google.accompanist.pager.ExperimentalPagerApi
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @ExperimentalPagerApi
    @ExperimentalAnimationApi
    @ExperimentalCoilApi
    @ExperimentalFoundationApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            WallpaperXTheme {
                Surface(modifier = Modifier.fillMaxSize(),color = Color.Black) {
                    ProvideWindowInsets {
                        WallpaperXApp()
                    }
                }
            }
        }
    }
}
