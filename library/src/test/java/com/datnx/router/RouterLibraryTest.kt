package com.datnx.router

import com.datnx.router.manager.RouteManager
import org.junit.Test
import org.junit.Assert.*

/**
 * Basic unit tests for the Router library
 */
class RouterLibraryTest {

    @Test
    fun testRouteManagerSingleton() {
        val instance1 = RouteManager.instance
        val instance2 = RouteManager.instance
        assertEquals("RouteManager should be singleton", instance1, instance2)
    }

    @Test
    fun testRouteManagerClearProviders() {
        val routeManager = RouteManager.instance
        routeManager.clearRouteProviders()
        assertTrue("Route providers should be empty after clear", 
            routeManager.getRouteProviders().isEmpty())
    }
}
