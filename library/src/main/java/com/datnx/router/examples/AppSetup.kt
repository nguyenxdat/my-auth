package com.datnx.router.examples

/**
 * Main App Setup
 * Demonstrates how to use the Router Manager system
 */


///**
// * Alternative setup with manual route provider registration
// */
//@Composable
//fun MyAuthAppAlternative() {
//    val navController = rememberNavController()
//
//    ProvideNavigation(navController = navController) {
//        // Register routes individually
//        RouteManager.instance.apply {
//            clearRouteProviders()
//            registerRouteProvider(PlatformRoute())
//            registerRouteProvider(AuthRoute())
//
//            // Setup navigation
//            setupRoutes(navController)()
//        }
//    }
//}
