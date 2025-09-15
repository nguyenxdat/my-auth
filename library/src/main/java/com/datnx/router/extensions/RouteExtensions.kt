package com.datnx.router.extensions

import androidx.navigation.NavController
import androidx.navigation.NavOptionsBuilder
import com.datnx.router.models.NavOptions
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import java.net.URLEncoder
import java.net.URLDecoder
import java.nio.charset.StandardCharsets

/**
 * Extension functions for navigation convenience
 */

/**
 * Navigate with custom options
 */
fun NavController.navigateWithOptions(
    route: String,
    navOptions: NavOptions?
) {
    navigate(route) {
        navOptions?.let { options ->
            options.popUpTo?.let { popUpTo ->
                popUpTo(popUpTo) {
                    inclusive = options.inclusive
                    saveState = options.saveState
                }
            }
            restoreState = options.restoreState
            launchSingleTop = options.singleTop
        }
    }
}

/**
 * Pop back stack to a route with options
 */
fun NavController.popBackStackTo(
    route: String,
    inclusive: Boolean = false,
    saveState: Boolean = false
): Boolean {
    return popBackStack(route, inclusive, saveState)
}

/**
 * Check if current destination matches route
 */
fun NavController.isCurrentDestination(route: String): Boolean {
    return currentDestination?.route == route
}

/**
 * Get current route name
 */
fun NavController.getCurrentRouteName(): String? {
    return currentDestination?.route
}

/**
 * Serialize object to JSON string for URL parameter
 */
inline fun <reified T> T.toJsonParam(): String {
    return URLEncoder.encode(
        Json.encodeToString(this), 
        StandardCharsets.UTF_8.toString()
    )
}

/**
 * Deserialize JSON string from URL parameter
 */
inline fun <reified T> String.fromJsonParam(): T? {
    return try {
        val decoded = URLDecoder.decode(this, StandardCharsets.UTF_8.toString())
        Json.decodeFromString<T>(decoded)
    } catch (e: Exception) {
        null
    }
}

/**
 * Build route with parameters
 */
fun String.buildRoute(vararg params: Pair<String, Any>): String {
    var route = this
    params.forEach { (key, value) ->
        route = route.replace("{$key}", value.toString())
    }
    return route
}

/**
 * Build route with query parameters
 */
fun String.buildRouteWithQuery(params: Map<String, Any>): String {
    if (params.isEmpty()) return this
    
    val queryString = params.entries.joinToString("&") { (key, value) ->
        "$key=${URLEncoder.encode(value.toString(), StandardCharsets.UTF_8.toString())}"
    }
    return "$this?$queryString"
}

/**
 * Extract parameters from route
 */
fun String.extractRouteParams(): Map<String, String> {
    val params = mutableMapOf<String, String>()
    if (contains("?")) {
        val queryString = substringAfter("?")
        queryString.split("&").forEach { param ->
            val (key, value) = param.split("=", limit = 2)
            params[key] = URLDecoder.decode(value, StandardCharsets.UTF_8.toString())
        }
    }
    return params
}

/**
 * Navigation DSL builder
 */
class NavigationBuilder {
    var route: String = ""
    var popUpTo: String? = null
    var inclusive: Boolean = false
    var saveState: Boolean = false
    var restoreState: Boolean = false
    var singleTop: Boolean = false
    
    fun build(): Pair<String, NavOptions> {
        return route to NavOptions(
            popUpTo = popUpTo,
            inclusive = inclusive,
            saveState = saveState,
            restoreState = restoreState,
            singleTop = singleTop
        )
    }
}

/**
 * Navigate using DSL
 */
fun NavController.navigateDsl(builder: NavigationBuilder.() -> Unit) {
    val navigationBuilder = NavigationBuilder().apply(builder)
    val (route, options) = navigationBuilder.build()
    navigateWithOptions(route, options)
}

/**
 * Safe navigation - won't crash if route doesn't exist
 */
fun NavController.safeNavigate(route: String): Boolean {
    return try {
        navigate(route)
        true
    } catch (e: Exception) {
        false
    }
}

/**
 * Navigate with timeout - useful for preventing double-tap issues
 */
private var lastNavigationTime = 0L
private const val NAVIGATION_TIMEOUT = 500L

fun NavController.navigateWithTimeout(route: String, timeout: Long = NAVIGATION_TIMEOUT): Boolean {
    val currentTime = System.currentTimeMillis()
    return if (currentTime - lastNavigationTime > timeout) {
        lastNavigationTime = currentTime
        safeNavigate(route)
    } else {
        false
    }
}
