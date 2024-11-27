package com.example.pouet

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import com.shirishkoirala.fontawesome.Icons
import androidx.compose.material3.*
import androidx.compose.material3.adaptive.currentWindowAdaptiveInfo
import androidx.compose.material3.adaptive.navigationsuite.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.*
import androidx.compose.ui.unit.dp
import com.shirishkoirala.fontawesome.ComposeIconView
import androidx.window.core.layout.*

@Composable
fun MyNavigationBar(
    windowClass: WindowSizeClass,
    viewModel: MainViewModel
) {

    val navItems = listOf(
        stringResource(R.string.page_movies),
        stringResource(R.string.page_series),
        stringResource(R.string.page_actors)
    )
    val navIcons = listOf(Icons.film, Icons.display, Icons.user)
    val adaptiveInfo = currentWindowAdaptiveInfo()
    val customNavSuiteType =
        with(adaptiveInfo) {
            if (windowSizeClass.windowWidthSizeClass == WindowWidthSizeClass.COMPACT) {
                NavigationSuiteType.NavigationBar
            } else
                NavigationSuiteType.NavigationRail
        }

    val myColors = NavigationSuiteItemColors(
        navigationDrawerItemColors = NavigationDrawerItemDefaults.colors(
            selectedIconColor = Color.White,
            selectedTextColor = Color.White,
            unselectedTextColor = Color.Gray,
            selectedContainerColor = colorResource(R.color.teal_700)
        ),
        navigationBarItemColors = NavigationBarItemDefaults.colors(
            selectedIconColor = Color.White,
            selectedTextColor = Color.White,
            unselectedTextColor = Color.Gray,
            indicatorColor = colorResource(R.color.teal_700),
        ),
        navigationRailItemColors = NavigationRailItemDefaults.colors(
            selectedIconColor = Color.White,
            selectedTextColor = Color.White,
            unselectedTextColor = Color.Gray,
            indicatorColor = colorResource(R.color.teal_700),
        )
    )

    NavigationSuiteScaffold(
        layoutType = customNavSuiteType,
        modifier = Modifier.systemBarsPadding(),
        containerColor = Color.Black,
        contentColor = Color.White,
        navigationSuiteColors = NavigationSuiteDefaults.colors(
            navigationDrawerContainerColor = Color.DarkGray,
            navigationRailContainerColor = Color.DarkGray,
            navigationBarContainerColor = Color.DarkGray,
        ),
        navigationSuiteItems = {
            navItems.forEachIndexed { index, navItem ->
                item(
                    icon = {
                        ComposeIconView(
                            navIcons[index],
                            size = 24.dp,
                            modifier = Modifier.padding(2.dp)
                        )
                    },
                    label = { Text(navItem) },
                    selected = viewModel.currentDestination.ordinal == index,
                    onClick = { viewModel.navigateTo(index) },
                    colors = myColors,

                    )
            }
        }
    ) {
        Scaffold(
            topBar = {
                if (viewModel.currentDestination == Destination.MOVIES || viewModel.currentDestination == Destination.SERIES || viewModel.currentDestination == Destination.ACTORS) {
                    MySearchBar(windowClass, viewModel)
                }
            },
            contentColor = Color.White,
            containerColor = Color.Black,
        ) { innerPadding ->
            Column(modifier = Modifier.padding(innerPadding)) {
                when (viewModel.currentDestination) {
                    Destination.MOVIES -> FilmsScreen(windowClass, viewModel)
                    Destination.SERIES -> SeriesScreen(windowClass, viewModel)
                    Destination.ACTORS -> ActorsScreen(windowClass, viewModel)
                    Destination.PROFILE -> HomeScreen(windowClass, viewModel)
                    Destination.DETAILS_MOVIES -> DetailMovieScreen(windowClass, viewModel)
                    Destination.DETAILS_SERIES -> DetailSerieScreen(windowClass, viewModel)
                    Destination.DETAILS_ACTORS -> DetailActorScreen(windowClass, viewModel)
                }
            }
        }
    }

}