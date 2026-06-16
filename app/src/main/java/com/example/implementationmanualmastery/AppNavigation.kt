package com.example.implementationmanualmastery

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.*
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import kotlinx.serialization.Serializable

// 1. Define strongly-typed routes using Kotlin Serialization
@Serializable object HomeScreen
@Serializable object ProfileScreen
@Serializable data class DetailsScreen(val itemId: String, val category: String)
@Preview(showSystemUi=true)
@Composable
fun AppNavigation() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = HomeScreen) {

        // Screen 1: Home
        composable<HomeScreen> {
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text("Home Screen", style = MaterialTheme.typography.headlineMedium)
                Spacer(modifier = Modifier.height(16.dp))
                Button(onClick = {
                    // Pass arguments safely without manually formatting a URL string
                    navController.navigate(DetailsScreen(itemId = "42", category = "Tech Books"))
                }) {
                    Text("Go to Details")
                }
            }
        }

        // Screen 2: Details (Receives Arguments)
        composable<DetailsScreen> { backStackEntry ->
            // 2. Extract arguments safely using type-safe extension functions
            val args = backStackEntry.toRoute<DetailsScreen>()

            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text("Details Screen", style = MaterialTheme.typography.headlineMedium)
                Text("Item ID: ${args.itemId}")
                Text("Category: ${args.category}") // Automatically URL-decoded under the hood!

                Spacer(modifier = Modifier.height(16.dp))
                Button(onClick = { navController.navigate(ProfileScreen) }) {
                    Text("Go to Profile")
                }
            }
        }

        // Screen 3: Profile
        composable<ProfileScreen> {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Text("Profile Screen", style = MaterialTheme.typography.headlineMedium)
            }
        }
    }
}