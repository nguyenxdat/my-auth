package com.datnx.my_auth.main

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.datnx.router.base.BaseRoute
import kotlin.apply
import kotlin.let

/**
 * Platform Route Implementation
 */
class TestPlatformRoute : BaseRoute() {
    override val prefix: String = PREFIX

    companion object {
        // Route definitions
        private const val PREFIX = "/platform"
        const val SPLASH_ROUTE = "/"
    }

    override fun registerRoute(navGraphBuilder: NavGraphBuilder) {
        navGraphBuilder.apply {
            // Splash/Root route
            composable(SPLASH_ROUTE) {
                TestSplashScreen()
            }
        }
    }

    override fun openScreen(
        navController: NavHostController,
        extraData: Map<String, Any>?
    ): Boolean {

        return false
    }

    override fun getRoutePaths(): List<String> {
        return listOf(
            SPLASH_ROUTE,
            )
    }
}




