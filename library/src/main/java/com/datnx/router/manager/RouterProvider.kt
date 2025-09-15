package com.datnx.router.manager

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.remember
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController

/**
 * Router provider composables for dependency injection
 */

/**
 * Provides AppRouter to the composition tree
 * 
 * @param navController Optional NavHostController, creates one if not provided
 * @param content Composable content that can access AppRouter
 */
@Composable
fun ProvideAppRouter(
    navController: NavHostController = rememberNavController(),
    content: @Composable (AppRouter) -> Unit
) {
    val appRouter = remember(navController) { AppRouter(navController) }
    
    CompositionLocalProvider(
        LocalAppRouter provides appRouter,
        LocalNavController provides navController
    ) {
        content(appRouter)
    }
}

/**
 * Provides both NavController and AppRouter to the composition tree
 * 
 * @param navController Optional NavHostController, creates one if not provided
 * @param content Composable content that can access both NavController and AppRouter
 */
@Composable
fun ProvideNavigation(
    navController: NavHostController = rememberNavController(),
    content: @Composable () -> Unit
) {
    val appRouter = remember(navController) { AppRouter(navController) }
    
    CompositionLocalProvider(
        LocalAppRouter provides appRouter,
        LocalNavController provides navController
    ) {
        content()
    }
}

/**
 * Hook to get AppRouter from composition
 */
@Composable
fun useAppRouter(): AppRouter {
    return LocalAppRouter.current
}

/**
 * Hook to get NavController from composition
 */
@Composable
fun useNavController(): NavHostController {
    return LocalNavController.current
}
