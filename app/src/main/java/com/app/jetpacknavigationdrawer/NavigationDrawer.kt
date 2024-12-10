package com.app.jetpacknavigationdrawer

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.app.jetpacknavigationdrawer.screens.GroceryListScreen
import com.app.jetpacknavigationdrawer.screens.HomeScreen
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Preview
@Composable
fun NavigationDrawer() {
    val navController = rememberNavController()
    val items = listOf(
        NavigationItems("Home", Icons.Filled.Home, Icons.Outlined.Home),
        NavigationItems("Info", Icons.Filled.Info, Icons.Outlined.Info),
        NavigationItems("Edit", Icons.Filled.Edit, Icons.Outlined.Edit, 105),
        NavigationItems("Settings", Icons.Filled.Settings, Icons.Outlined.Settings)
    )

    var selectedItemIndex by rememberSaveable { mutableStateOf(0) }
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            ModalDrawerSheet {
                Spacer(modifier = Modifier.height(16.dp))
                items.forEachIndexed { index, item ->
                    NavigationDrawerItem(
                        label = { Text(text = item.title) },
                        selected = index == selectedItemIndex,
                        onClick = {
                            selectedItemIndex = index
                            scope.launch {
                                drawerState.close()
                            }
                            when (index) {
                                0 -> navController.navigate("home")
                                1 -> navController.navigate("info")
                                2 -> navController.navigate("edit")
                                3 -> navController.navigate("settings")
                            }
                        },
                        icon = {
                            Icon(
                                imageVector = if (index == selectedItemIndex) item.selectedIcon else item.unselectedIcon,
                                contentDescription = item.title
                            )
                        },
                        badge = {
                            item.badgeCount?.let { Text(text = it.toString()) }
                        },
                        modifier = Modifier.padding(NavigationDrawerItemDefaults.ItemPadding)
                    )
                }
            }
        }
    ) {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = { Text(text = "Navigation Drawer Example") },
                    navigationIcon = {
                        IconButton(onClick = {
                            scope.launch {
                                drawerState.apply {
                                    if (isClosed) open() else close()
                                }
                            }
                        }) {
                            Icon(
                                imageVector = Icons.Default.Menu,
                                contentDescription = "Menu"
                            )
                        }
                    }
                )
            }
        ) { paddingValues ->
            // Set up the navigation host
            AppNavHost(navController = navController, modifier = Modifier.padding(paddingValues))
        }
    }
}

@Composable
fun AppNavHost(navController: NavHostController, modifier: Modifier = Modifier) {
    NavHost(navController = navController, startDestination = "home", modifier = modifier) {
        composable("home") { HomeScreen() }
        composable("info") { GroceryListScreen() }
        composable("edit") { HomeScreen() }
        composable("settings") { HomeScreen() }
    }
}
