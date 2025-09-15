package com.datnx.my_auth.main

import androidx.compose.runtime.Composable
import androidx.navigation.compose.rememberNavController
import com.datnx.my_auth.manager.RouteManager
import com.datnx.router.AuthRoute
import com.datnx.router.manager.ProvideNavigation

@Composable
fun MyAuthApp() {
    val navController = rememberNavController()
    val routeManager = RouteManager.instance
    
    // Register all route providers
    routeManager.apply {
        clearRouteProviders() // Clear any existing providers
        registerRouteProviders(
            AuthRoute(),
        )
    }
    
    // Provide navigation context to the app
    ProvideNavigation(navController = navController) {
        // Setup routes using RouteManager
        routeManager.setupRoutes(
            navController = navController,
            startDestination = AuthRoute.LOGIN_ROUTE
        )()
    }
}