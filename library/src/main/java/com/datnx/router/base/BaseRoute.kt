package com.datnx.router.base

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController

/**
 * Base abstract class for route modules
 * Similar to Flutter's BaseRoute abstract class
 */
abstract class BaseRoute {
    /**
     * Route prefix for this module
     * E.g., "/auth", "/platform", "/transfer"
     */
    abstract val prefix: String
    
    /**
     * Register routes for this module in NavGraphBuilder
     * Similar to registerRoute() in Flutter
     * 
     * @param navGraphBuilder NavGraphBuilder to register routes
     */
    abstract fun registerRoute(navGraphBuilder: NavGraphBuilder)
    
    /**
     * Open screen programmatically with extra data
     * Similar to openScreen() in Flutter
     * 
     * @param navController Navigation controller
     * @param extraData Extra data to pass to the screen
     * @return Boolean indicating if the navigation was successful
     */
    abstract fun openScreen(
        navController: NavHostController,
        extraData: Map<String, Any>? = null
    ): Boolean
    
    /**
     * Get list of route paths for this module
     */
    open fun getRoutePaths(): List<String> = emptyList()
    
    /**
     * Check if a route belongs to this module
     */
    open fun belongsToModule(route: String): Boolean {
        return route.startsWith(prefix)
    }
}
