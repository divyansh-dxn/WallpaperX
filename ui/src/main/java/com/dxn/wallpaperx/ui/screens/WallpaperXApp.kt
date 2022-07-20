package com.dxn.wallpaperx.ui.screens

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.rememberSplineBasedDecay
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.*
import androidx.paging.compose.collectAsLazyPagingItems
import coil.compose.rememberImagePainter
import com.dxn.wallpaperx.ui.MainViewModel
import com.dxn.wallpaperx.ui.components.WallpaperCard
import com.dxn.wallpaperx.ui.navigation.HomeScreen.Wallpapers
import com.dxn.wallpaperx.ui.navigation.HomeScreen.Collections
import com.dxn.wallpaperx.ui.navigation.HomeScreen.Favourites
import com.dxn.wallpaperx.ui.navigation.HomeScreen.Setting
import com.dxn.wallpaperx.ui.navigation.RootScreen
import com.google.accompanist.systemuicontroller.rememberSystemUiController

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WallpaperXApp() {
    val navController = rememberNavController()
    val systemUiController = rememberSystemUiController()
    val backgroundColor = MaterialTheme.colorScheme.background
    val isHomeScreen by remember { mutableStateOf(true) }
    SideEffect {
        systemUiController.setNavigationBarColor(backgroundColor)
        systemUiController.setStatusBarColor(backgroundColor)
    }
    val mainViewModel: MainViewModel = hiltViewModel()
    val wallpapers = mainViewModel.wallpapers.collectAsLazyPagingItems()
    val collections = mainViewModel.collections.collectAsLazyPagingItems()
    val favourites by remember { mainViewModel.favourites }
    val wallpaperListState = rememberLazyGridState()
    val favouriteListState = rememberLazyGridState()
    val collectionListState = rememberLazyListState()
    val screens = listOf(Wallpapers, Collections, Favourites, Setting)

    val launcher =
        rememberLauncherForActivityResult(contract = ActivityResultContracts.GetContent()) { uri: Uri? ->
            val data = Uri.encode(uri.toString())
            navController.navigate(RootScreen.Gallery.route.plus("/${data}"))
        }

    var selectedItem by remember { mutableStateOf(0) }
    val items = listOf("Home", "Collections", "Favourites", "Settings")
    val decayAnimationSpec = rememberSplineBasedDecay<Float>()
    val scrollBehavior = TopAppBarDefaults.exitUntilCollapsedScrollBehavior(
        decayAnimationSpec,
        rememberTopAppBarScrollState()
    )
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination
    val currentRoute = navBackStackEntry?.destination?.route
    var currentTitle: String by remember { mutableStateOf(Wallpapers.title) }

    Scaffold(
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
//            if(screens.any { it.route == currentRoute }) {
                LargeTopAppBar(
                    colors = TopAppBarDefaults.largeTopAppBarColors(scrolledContainerColor = MaterialTheme.colorScheme.surface),
                    title = { Text(currentTitle) },
                    actions = {
                        IconButton(onClick = { navController.navigate(RootScreen.Search.route) }) {
                            Icon(
                                imageVector = Icons.Filled.Search,
                                contentDescription = "Localized description"
                            )
                        }
                    },
                    scrollBehavior = scrollBehavior
                )
//            }
        },
        bottomBar = {
            NavigationBar {
                screens.forEachIndexed { index, screen ->
                    NavigationBarItem(
                        icon = {
                            Icon(
                                imageVector = screen.icon,
                                contentDescription = null
                            )
                        },
                        selected = currentDestination?.hierarchy?.any { it.route == screen.route } == true,
                        onClick = {
                            navController.navigate(screen.route) {
                                // Pop up to the start destination of the graph to
                                // avoid building up a large stack of destinations
                                // on the back stack as users select items
                                popUpTo(navController.graph.findStartDestination().id) {
                                    saveState = true
                                }
                                // Avoid multiple copies of the same destination when
                                // reselecting the same item
                                launchSingleTop = true
                                // Restore state when reselecting a previously selected item
                                restoreState = true
                                currentTitle = screen.title
                            }
                        }
                    )
                }
            }
        }
    ) { innerPadding ->
        NavHost(
            modifier = Modifier.padding(horizontal = 4.dp),
            navController = navController,
            startDestination = "home"
        ) {
            homeGraph(navController, innerPadding)
            composable(RootScreen.Search.route) {
                LazyColumn(contentPadding = innerPadding) {
                    items(30) { Text(text = "${RootScreen.Search.title} $it") }
                }
            }
            composable("set_wallpaper") {

            }
            composable("set_wallpaper_local") {

            }
        }
    }
}

fun NavGraphBuilder.homeGraph(navController: NavController, contentPadding: PaddingValues) {
    navigation(startDestination = Wallpapers.route, route = "home") {
        composable(Wallpapers.route) {
            LazyColumn(contentPadding = contentPadding) {
                items(30) { Text(text = "${RootScreen.Search.title} $it") }
            }
        }
        composable(Collections.route) {
            LazyColumn(contentPadding = contentPadding) {
                items(30) { Text(text = "${RootScreen.Search.title} $it") }
            }
        }
        composable(Favourites.route) {
            LazyColumn(contentPadding = contentPadding) {
                items(30) { Text(text = "${RootScreen.Search.title} $it") }
            }
        }
        composable(Setting.route) {
            LazyColumn(contentPadding = contentPadding) {
                items(30) { Text(text = "${RootScreen.Search.title} $it") }
            }
        }
    }
}
