package com.datnx.router.manager

import androidx.navigation.NavHostController

/**
 * App Router - Integration layer for easy navigation
 * Wraps NavController and RouteManager for convenience
 */
class AppRouter(private val navController: NavHostController) {

    // Navigation state
    val currentBackStack = navController.currentBackStackEntryFlow
    val canGoBack get() = navController.previousBackStackEntry != null

    // MARK: - Basic Navigation Methods

    /**
     * Navigate to a route
     */
    fun navigate(route: String) {
        navController.navigate(route)
    }

    /**
     * Navigate to a route with options
     */
    fun navigate(
        route: String,
        popUpTo: String? = null,
        inclusive: Boolean = false,
        saveState: Boolean = false,
        restoreState: Boolean = false
    ) {
        navController.navigate(route) {
            popUpTo?.let {
                popUpTo(it) {
                    this.inclusive = inclusive
                    this.saveState = saveState
                }
            }
            this.restoreState = restoreState
        }
    }

    /**
     * Pop back stack
     */
    fun pop(): Boolean {
        return navController.popBackStack()
    }

    /**
     * Pop to a specific route
     */
    fun popTo(route: String, inclusive: Boolean = false): Boolean {
        return navController.popBackStack(route, inclusive)
    }

    /**
     * Pop to root
     */
    fun popToRoot() {
        navController.popBackStack("/", inclusive = false)
    }

    /**
     * Replace current screen
     */
    fun replace(route: String) {
        val currentRoute = navController.currentDestination?.route
        navController.navigate(route) {
            popUpTo(currentRoute ?: "/") {
                inclusive = true
            }
        }
    }

    /**
     * Replace all screens and navigate
     */
    fun replaceAll(route: String) {
        navController.navigate(route) {
            popUpTo("/") {
                inclusive = true
            }
        }
    }

    /**
     * Push and clear entire stack
     */
    fun pushAndClearStack(route: String) {
        navController.navigate(route) {
            popUpTo(0) { inclusive = true }
        }
    }


    // MARK: - Utility Methods

    /**
     * Get current route
     */
    fun getCurrentRoute(): String? {
        return navController.currentDestination?.route
    }



}
