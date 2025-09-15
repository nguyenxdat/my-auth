package com.datnx.my_auth.manager

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.datnx.router.base.BaseRoute

/**
 * Route Manager - Singleton class to manage all route modules
 * Similar to Flutter's RouteManager
 */
class RouteManager private constructor() {
    
    companion object {
        @Volatile
        private var INSTANCE: RouteManager? = null
        
        val instance: RouteManager
            get() = INSTANCE ?: synchronized(this) {
                INSTANCE ?: RouteManager().also { INSTANCE = it }
            }
    }
    
    private val _routeProviders = mutableListOf<BaseRoute>()
    
    /**
     * Register a route provider
     * 
     * @param routeProvider BaseRoute implementation to register
     */
    fun registerRouteProvider(routeProvider: BaseRoute) {
        _routeProviders.add(routeProvider)
    }
    
    /**
     * Register multiple route providers
     * 
     * @param routeProviders List of BaseRoute implementations to register
     */
    fun registerRouteProviders(vararg routeProviders: BaseRoute) {
        _routeProviders.addAll(routeProviders)
    }
    
    /**
     * Clear all registered route providers
     */
    fun clearRouteProviders() {
        _routeProviders.clear()
    }
    
    /**
     * Setup all routes in NavHost
     * Similar to routes() in Flutter
     * 
     * @param navController Navigation controller
     * @param startDestination Starting destination route
     * @return Composable function for NavHost
     */
    fun setupRoutes(
        navController: NavHostController,
        startDestination: String = "/"
    ): @Composable () -> Unit = {
        NavHost(
            navController = navController,
            startDestination = startDestination
        ) {
            // Register all routes from providers
            _routeProviders.forEach { routeProvider ->
                routeProvider.registerRoute(this)
            }
        }
    }
    
    /**
     * Open screen programmatically using prefix
     * Similar to openScreen() in Flutter
     * 
     * @param navController Navigation controller
     * @param prefix Route module prefix
     * @param extraData Extra data to pass
     * @return Boolean indicating success
     */
    fun openScreen(
        navController: NavHostController,
        prefix: String,
        extraData: Map<String, Any>? = null
    ): Boolean {
        val route = _routeProviders.find { 
            it.prefix.removePrefix("/") == prefix 
        }
        
        return route?.openScreen(navController, extraData) ?: false
    }
    
    /**
     * Check if path is valid
     * Similar to isInValidPath() in Flutter
     * 
     * @param path Route path to validate
     * @return Boolean indicating if path is invalid
     */
    fun isInValidPath(path: String): Boolean {
        val found = _routeProviders.count { routeProvider ->
            path.startsWith(routeProvider.prefix)
        }
        return found == 0
    }
    
    /**
     * Get all registered route providers
     */
    fun getRouteProviders(): List<BaseRoute> = _routeProviders.toList()
    
    /**
     * Get route provider by prefix
     */
    fun getRouteProvider(prefix: String): BaseRoute? {
        return _routeProviders.find { it.prefix == prefix }
    }
    
    /**
     * Get all available route paths
     */
    fun getAllRoutePaths(): List<String> {
        return _routeProviders.flatMap { it.getRoutePaths() }
    }
    
    /**
     * Find route provider that handles a specific route
     */
    fun findRouteProvider(route: String): BaseRoute? {
        return _routeProviders.find { it.belongsToModule(route) }
    }
}
