# My Auth Router Library

A Flutter-inspired navigation router library for Jetpack Compose, providing modular route management similar to Flutter's route system.

## Features

- **Modular Route Management**: Organize routes by modules (Platform, Auth, Transfer, etc.)
- **Flutter-like API**: Similar to Flutter's `Navigator.pushNamed()` and route management
- **Type-safe Navigation**: Support for both type-safe and string-based navigation
- **Programmatic Navigation**: Open screens with custom data using prefix-based routing
- **Extension Functions**: Convenient extension functions for common navigation patterns
- **Composition Local Integration**: Easy dependency injection throughout the app

## Quick Start

### 1. Setup your app

```kotlin
@Composable
fun MyApp() {
    MaterialTheme {
        MyAuthApp() // Uses the router system
    }
}
```

### 2. Create a route module

```kotlin
class MyRoute : BaseRoute() {
    override val prefix: String = "/my_module"
    
    companion object {
        const val HOME_ROUTE = "/my_module/home"
        const val DETAIL_ROUTE = "/my_module/detail"
    }
    
    override fun registerRoute(navGraphBuilder: NavGraphBuilder) {
        navGraphBuilder.apply {
            composable(HOME_ROUTE) {
                HomeScreen()
            }
            
            composable("$DETAIL_ROUTE?id={id}") { backStackEntry ->
                val id = backStackEntry.arguments?.getString("id") ?: ""
                DetailScreen(id = id)
            }
        }
    }
    
    override fun openScreen(
        navController: NavHostController,
        extraData: Map<String, Any>?
    ): Boolean {
        extraData?.let { data ->
            when (data["screen"]) {
                "home" -> {
                    navController.navigate(HOME_ROUTE)
                    return true
                }
                "detail" -> {
                    val id = data["id"] as? String ?: ""
                    navController.navigate("$DETAIL_ROUTE?id=$id")
                    return true
                }
            }
        }
        return false
    }
}
```

### 3. Register routes in your app

```kotlin
@Composable
fun MyApp() {
    val navController = rememberNavController()
    
    ProvideNavigation(navController = navController) {
        RouteManager.instance.apply {
            clearRouteProviders()
            registerRouteProviders(
                PlatformRoute(),
                AuthRoute(),
                TransferRoute(),
                MyRoute() // Your custom route
            )
            
            setupRoutes(navController)()
        }
    }
}
```

### 4. Navigate in your screens

```kotlin
@Composable
fun MyScreen() {
    val appRouter = useAppRouter()
    
    Button(
        onClick = {
            // Type-safe navigation
            appRouter.navigate("/auth/login")
            
            // Or Flutter-like navigation
            appRouter.openScreen(
                prefix = "auth",
                extraData = mapOf("screen" to "login")
            )
        }
    ) {
        Text("Go to Login")
    }
}
```

## API Reference

### BaseRoute

Abstract class that defines a route module:

```kotlin
abstract class BaseRoute {
    abstract val prefix: String
    abstract fun registerRoute(navGraphBuilder: NavGraphBuilder)
    abstract fun openScreen(navController: NavHostController, extraData: Map<String, Any>?): Boolean
}
```

### RouteManager

Singleton that manages all route modules:

```kotlin
RouteManager.instance.apply {
    registerRouteProvider(routeProvider)
    openScreen(navController, prefix, extraData)
    isInValidPath(path)
}
```

### AppRouter

Navigation wrapper with convenience methods:

```kotlin
val appRouter = useAppRouter()

// Basic navigation
appRouter.navigate(route)
appRouter.pop()
appRouter.popToRoot()

// Advanced navigation
appRouter.replace(route)
appRouter.pushAndClearStack(route)

// Flutter-like navigation
appRouter.openScreen(prefix, extraData)
```

### Extension Functions

Convenient extensions for navigation:

```kotlin
// Build routes with parameters
val route = "/user/{id}".buildRoute("id" to userId)

// Safe navigation
navController.safeNavigate(route)

// Navigation with timeout (prevents double-tap)
navController.navigateWithTimeout(route)

// Serialize objects for URL parameters
val jsonParam = myObject.toJsonParam()
val decoded = jsonParam.fromJsonParam<MyObject>()
```

## Example Implementations

The library includes three example route modules:

1. **PlatformRoute** (`/platform`): Main app screens (splash, main, contact, etc.)
2. **AuthRoute** (`/auth`): Authentication screens (login, register, forgot password)
3. **TransferRoute** (`/transfer`): Transfer-related screens (list, detail, create)

Each example demonstrates different patterns:
- Passing complex data objects
- URL parameter handling
- Programmatic navigation
- Screen-to-screen communication

## Migration from Flutter

If you're coming from Flutter, here's how concepts map:

| Flutter | Jetpack Compose Router |
|---------|----------------------|
| `Navigator.pushNamed(context, '/route')` | `appRouter.openScreen("module", mapOf("screen" to "route"))` |
| `Navigator.pop(context)` | `appRouter.pop()` |
| `Navigator.pushAndRemoveUntil()` | `appRouter.pushAndClearStack()` |
| `ModalRoute.of(context).settings.arguments` | URL parameters + JSON serialization |
| Route registration in MaterialApp | `BaseRoute.registerRoute()` |

## Building the Library

1. Clone the repository
2. Open in Android Studio
3. Build the library module:
   ```bash
   ./gradlew :library:build
   ```

## Testing

Run the sample app to see the router in action:

```bash
./gradlew :app:installDebug
```

The sample app demonstrates:
- Multi-module navigation
- Passing data between screens
- Back stack management
- Debug information display

## License

MIT License - see LICENSE file for details.
