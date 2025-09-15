package com.datnx.router.manager

import androidx.compose.runtime.compositionLocalOf
import androidx.navigation.NavHostController

/**
 * Composition locals for dependency injection
 */

/**
 * CompositionLocal for AppRouter
 * Provides AppRouter instance to the composition tree
 */
val LocalAppRouter = compositionLocalOf<AppRouter> {
    error("AppRouter not provided. Make sure to wrap your app with ProvideAppRouter.")
}

/**
 * CompositionLocal for NavHostController
 * Provides NavHostController instance to the composition tree
 */
val LocalNavController = compositionLocalOf<NavHostController> {
    error("NavHostController not provided. Make sure to wrap your app with ProvideNavController.")
}
