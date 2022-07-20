package com.dxn.wallpaperx.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import coil.annotation.ExperimentalCoilApi
import coil.compose.ImagePainter
import coil.compose.rememberImagePainter
import coil.transform.BlurTransformation
import com.dxn.wallpaperx.data.model.Wallpaper

@ExperimentalCoilApi
@Composable
fun WallpaperCard(
    modifier: Modifier = Modifier,
    wallpaper: Wallpaper,
    isFavourite: Boolean,
    onLikedClicked: () -> Unit,
    onClick: () -> Unit,
    elevation: Dp = 4.dp,
    shape: Shape = MaterialTheme.shapes.medium,
) {
    Surface(modifier = modifier, elevation = elevation, shape = shape) {
        Box(Modifier.fillMaxSize()) {
            val loader = rememberImagePainter(
                data = wallpaper.previewUrl,
                builder = {
                    transformations(BlurTransformation(LocalContext.current))
                }
            )
            val painter = rememberImagePainter(wallpaper.smallUrl)

            if (painter.state.javaClass == ImagePainter.State.Loading::class.java) {
                Image(
                    modifier = Modifier.matchParentSize(),
                    painter = loader,
                    contentDescription = "wallpaper",
                    contentScale = ContentScale.Crop
                )
            }
            Image(
                modifier = Modifier.matchParentSize(),
                painter = painter,
                contentDescription = "wallpaper",
                contentScale = ContentScale.Crop
            )
            Box(modifier = Modifier
                .matchParentSize()
                .clickable {
                    onClick()
                }) {
                FavouriteButton(
                    modifier = Modifier
                        .align(Alignment.BottomEnd)
                        .padding(12.dp),
                    isFavourite = isFavourite
                ) {
                    onLikedClicked()
                }
            }
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WallpaperCard(
    onClick: () -> Unit,
    onLikedClicked: () -> Unit,
    painter: ImagePainter,
    isFavourite: Boolean
) {
    Card(
        modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth()
            .height(240.dp),
        onClick = { },
        shape = androidx.compose.material3.MaterialTheme.shapes.large
    ) {
        Box(Modifier.fillMaxSize()) {
            Image(
                modifier = Modifier.matchParentSize(),
                painter = painter,
                contentDescription = "wallpaper",
                contentScale = ContentScale.Crop
            )
            IconButton(
                onClick = { },
                modifier = Modifier.align(Alignment.BottomEnd)
            ) {
                Icon(
                    imageVector = if (isFavourite) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
                    contentDescription = "favourite button",
                    tint = Color.Red
                )
            }
        }
    }
}