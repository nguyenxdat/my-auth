//package com.datnx.router.examples
//
//import androidx.compose.foundation.layout.*
//import androidx.compose.foundation.lazy.LazyColumn
//import androidx.compose.foundation.lazy.items
//import androidx.compose.material3.*
//import androidx.compose.runtime.*
//import androidx.compose.ui.Alignment
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.unit.dp
//import androidx.navigation.NavGraphBuilder
//import androidx.navigation.NavHostController
//import androidx.navigation.compose.composable
//import com.datnx.router.base.BaseRoute
//import com.datnx.router.extensions.fromJsonParam
//import com.datnx.router.extensions.toJsonParam
//import com.datnx.router.manager.useAppRouter
//import com.datnx.router.models.BirthDayModel
//import com.datnx.router.models.NewsDetailScreenArgs
//
///**
// * Platform Route Implementation
// * Similar to Flutter's PlatformRoute
// */
//class PlatformRoute : BaseRoute() {
//    override val prefix: String = "/platform"
//
//    companion object {
//        // Route definitions
//        const val SPLASH_ROUTE = "/"
//        const val MAIN_ROUTE = "/platform/main"
//        const val CONTACT_US_ROUTE = "/platform/contact_us"
//        const val AWARDS_CERTIFICATES_ROUTE = "/platform/awards_certificates"
//        const val BIRTHDAY_ROUTE = "/platform/birthday"
//        const val NEWS_DETAIL_ROUTE = "/platform/news_detail"
//    }
//
//    override fun registerRoute(navGraphBuilder: NavGraphBuilder) {
//        navGraphBuilder.apply {
//            // Splash/Root route
//            composable(SPLASH_ROUTE) {
//                SplashScreen()
//            }
//
//            // Main screen
//            composable(MAIN_ROUTE) {
//                MainScreen()
//            }
//
//            // Contact Us screen
//            composable(CONTACT_US_ROUTE) {
//                ContactUsScreen()
//            }
//
//            // Awards & Certificates screen
//            composable(AWARDS_CERTIFICATES_ROUTE) {
//                AwardsCertificatesScreen()
//            }
//
//            // Birthday screen with arguments
//            composable(
//                route = "$BIRTHDAY_ROUTE?birthDaysJson={birthDaysJson}"
//            ) { backStackEntry ->
//                val birthDaysJson = backStackEntry.arguments?.getString("birthDaysJson")
//                val birthDays = birthDaysJson?.fromJsonParam<List<BirthDayModel>>() ?: emptyList()
//
//                BirthDayScreen(birthDays = birthDays)
//            }
//
//            // News Detail screen with arguments
//            composable(
//                route = "$NEWS_DETAIL_ROUTE?argsJson={argsJson}"
//            ) { backStackEntry ->
//                val argsJson = backStackEntry.arguments?.getString("argsJson")
//                val args = argsJson?.fromJsonParam<NewsDetailScreenArgs>()
//                    ?: NewsDetailScreenArgs("", "", "")
//
//                NewsDetailScreen(args = args)
//            }
//        }
//    }
//
//    override fun openScreen(
//        navController: NavHostController,
//        extraData: Map<String, Any>?
//    ): Boolean {
//        extraData?.let { data ->
//            when (data["screen"]) {
//                "main" -> {
//                    navController.navigate(MAIN_ROUTE)
//                    return true
//                }
//                "contact_us" -> {
//                    navController.navigate(CONTACT_US_ROUTE)
//                    return true
//                }
//                "awards_certificates" -> {
//                    navController.navigate(AWARDS_CERTIFICATES_ROUTE)
//                    return true
//                }
//                "birthday" -> {
//                    val birthDays = data["birthDays"] as? List<BirthDayModel>
//                    if (birthDays != null) {
//                        val birthDaysJson = birthDays.toJsonParam()
//                        navController.navigate("$BIRTHDAY_ROUTE?birthDaysJson=$birthDaysJson")
//                        return true
//                    }
//                }
//                "news_detail" -> {
//                    val args = data["args"] as? NewsDetailScreenArgs
//                    if (args != null) {
//                        val argsJson = args.toJsonParam()
//                        navController.navigate("$NEWS_DETAIL_ROUTE?argsJson=$argsJson")
//                        return true
//                    }
//                }
//            }
//        }
//        return false
//    }
//
//    override fun getRoutePaths(): List<String> {
//        return listOf(
//            SPLASH_ROUTE,
//            MAIN_ROUTE,
//            CONTACT_US_ROUTE,
//            AWARDS_CERTIFICATES_ROUTE,
//            BIRTHDAY_ROUTE,
//            NEWS_DETAIL_ROUTE
//        )
//    }
//}
//
//// MARK: - Screen Composables
//
//@OptIn(ExperimentalMaterial3Api::class)
//@Composable
//fun SplashScreen() {
//    val appRouter = useAppRouter()
//
//    Scaffold(
//        topBar = {
//            TopAppBar(title = { Text("Splash") })
//        }
//    ) { paddingValues ->
//        Column(
//            modifier = Modifier
//                .fillMaxSize()
//                .padding(paddingValues)
//                .padding(16.dp),
//            horizontalAlignment = Alignment.CenterHorizontally,
//            verticalArrangement = Arrangement.Center
//        ) {
//            Text(
//                text = "Welcome to My Auth App",
//                style = MaterialTheme.typography.headlineMedium
//            )
//
//            Spacer(modifier = Modifier.height(32.dp))
//
//            Button(
//                onClick = {
//                    appRouter.navigate(PlatformRoute.MAIN_ROUTE)
//                },
//                modifier = Modifier.fillMaxWidth()
//            ) {
//                Text("Get Started")
//            }
//        }
//    }
//}
//
//@OptIn(ExperimentalMaterial3Api::class)
//@Composable
//fun MainScreen() {
//    val appRouter = useAppRouter()
//    var backStackRoutes by remember { mutableStateOf(emptyList<String>()) }
//
//    LaunchedEffect(appRouter.currentBackStack) {
//        appRouter.currentBackStack.collect {
//            backStackRoutes = appRouter.getBackStackRoutes()
//        }
//    }
//
//    Scaffold(
//        topBar = {
//            TopAppBar(
//                title = { Text("Main") },
//                navigationIcon = {
//                    if (appRouter.canGoBack) {
//                        IconButton(onClick = { appRouter.pop() }) {
//                            Text("←")
//                        }
//                    }
//                }
//            )
//        }
//    ) { paddingValues ->
//        Column(
//            modifier = Modifier
//                .fillMaxSize()
//                .padding(paddingValues)
//                .padding(16.dp),
//            verticalArrangement = Arrangement.spacedBy(16.dp)
//        ) {
//            Text(
//                text = "Main Screen",
//                style = MaterialTheme.typography.headlineMedium
//            )
//
//            // Navigation Buttons
//            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
//                Button(
//                    onClick = {
//                        appRouter.navigate(PlatformRoute.CONTACT_US_ROUTE)
//                    },
//                    modifier = Modifier.fillMaxWidth()
//                ) {
//                    Text("Go to Contact Us")
//                }
//
//                Button(
//                    onClick = {
//                        appRouter.navigate(PlatformRoute.AWARDS_CERTIFICATES_ROUTE)
//                    },
//                    modifier = Modifier.fillMaxWidth()
//                ) {
//                    Text("Go to Awards & Certificates")
//                }
//
//                Button(
//                    onClick = {
//                        // Using openScreen method
//                        appRouter.openScreen(
//                            prefix = "platform",
//                            extraData = mapOf(
//                                "screen" to "birthday",
//                                "birthDays" to listOf(
//                                    BirthDayModel("1", "John Doe", "2024-01-15", 25),
//                                    BirthDayModel("2", "Jane Smith", "2024-02-20", 30)
//                                )
//                            )
//                        )
//                    },
//                    modifier = Modifier.fillMaxWidth()
//                ) {
//                    Text("Go to Birthday (with data)")
//                }
//
//                Button(
//                    onClick = {
//                        appRouter.openScreen(
//                            prefix = "platform",
//                            extraData = mapOf(
//                                "screen" to "news_detail",
//                                "args" to NewsDetailScreenArgs(
//                                    newsId = "news123",
//                                    title = "Breaking News",
//                                    content = "This is a sample news content...",
//                                    imageUrl = "https://example.com/image.jpg",
//                                    author = "John Reporter"
//                                )
//                            )
//                        )
//                    },
//                    modifier = Modifier.fillMaxWidth()
//                ) {
//                    Text("Go to News Detail")
//                }
//
//                OutlinedButton(
//                    onClick = {
//                        // Navigate to auth module (will be handled by AuthRoute)
//                        appRouter.openScreen(
//                            prefix = "auth",
//                            extraData = mapOf("screen" to "login")
//                        )
//                    },
//                    modifier = Modifier.fillMaxWidth()
//                ) {
//                    Text("Go to Login")
//                }
//            }
//
//            // Debug Information
//            Card(
//                modifier = Modifier.fillMaxWidth()
//            ) {
//                Column(
//                    modifier = Modifier.padding(16.dp)
//                ) {
//                    Text(
//                        text = "Navigation Debug",
//                        style = MaterialTheme.typography.titleMedium
//                    )
//                    Spacer(modifier = Modifier.height(8.dp))
//                    Text("Current Route: ${appRouter.getCurrentRoute()}")
//                    Text("Can Go Back: ${appRouter.canGoBack}")
//
//                    if (backStackRoutes.isNotEmpty()) {
//                        Text("Back Stack:")
//                        LazyColumn(
//                            modifier = Modifier.height(100.dp)
//                        ) {
//                            items(backStackRoutes) { route ->
//                                Text("• $route", style = MaterialTheme.typography.bodySmall)
//                            }
//                        }
//                    }
//                }
//            }
//        }
//    }
//}
//
//@OptIn(ExperimentalMaterial3Api::class)
//@Composable
//fun ContactUsScreen() {
//    val appRouter = useAppRouter()
//
//    Scaffold(
//        topBar = {
//            TopAppBar(
//                title = { Text("Contact Us") },
//                navigationIcon = {
//                    if (appRouter.canGoBack) {
//                        IconButton(onClick = { appRouter.pop() }) {
//                            Text("←")
//                        }
//                    }
//                }
//            )
//        }
//    ) { paddingValues ->
//        Column(
//            modifier = Modifier
//                .fillMaxSize()
//                .padding(paddingValues)
//                .padding(16.dp),
//            verticalArrangement = Arrangement.spacedBy(16.dp)
//        ) {
//            Text(
//                text = "Contact Us Screen",
//                style = MaterialTheme.typography.headlineMedium
//            )
//
//            Card(
//                modifier = Modifier.fillMaxWidth()
//            ) {
//                Column(
//                    modifier = Modifier.padding(16.dp)
//                ) {
//                    Text("Email: contact@myauth.com")
//                    Text("Phone: +1 234 567 8900")
//                    Text("Address: 123 Main St, City, Country")
//                }
//            }
//
//            Button(
//                onClick = { appRouter.pop() },
//                modifier = Modifier.fillMaxWidth()
//            ) {
//                Text("Go Back")
//            }
//        }
//    }
//}
//
//@OptIn(ExperimentalMaterial3Api::class)
//@Composable
//fun AwardsCertificatesScreen() {
//    val appRouter = useAppRouter()
//
//    Scaffold(
//        topBar = {
//            TopAppBar(
//                title = { Text("Awards & Certificates") },
//                navigationIcon = {
//                    if (appRouter.canGoBack) {
//                        IconButton(onClick = { appRouter.pop() }) {
//                            Text("←")
//                        }
//                    }
//                }
//            )
//        }
//    ) { paddingValues ->
//        Column(
//            modifier = Modifier
//                .fillMaxSize()
//                .padding(paddingValues)
//                .padding(16.dp),
//            verticalArrangement = Arrangement.spacedBy(16.dp)
//        ) {
//            Text(
//                text = "Awards & Certificates",
//                style = MaterialTheme.typography.headlineMedium
//            )
//
//            LazyColumn(
//                verticalArrangement = Arrangement.spacedBy(8.dp)
//            ) {
//                items(
//                    listOf(
//                        "Best Mobile App 2024",
//                        "Security Excellence Award",
//                        "Innovation in Authentication",
//                        "User Experience Award"
//                    )
//                ) { award ->
//                    Card(
//                        modifier = Modifier.fillMaxWidth()
//                    ) {
//                        Text(
//                            text = award,
//                            modifier = Modifier.padding(16.dp),
//                            style = MaterialTheme.typography.bodyLarge
//                        )
//                    }
//                }
//            }
//        }
//    }
//}
//
//@OptIn(ExperimentalMaterial3Api::class)
//@Composable
//fun BirthDayScreen(birthDays: List<BirthDayModel>) {
//    val appRouter = useAppRouter()
//
//    Scaffold(
//        topBar = {
//            TopAppBar(
//                title = { Text("Birthdays") },
//                navigationIcon = {
//                    if (appRouter.canGoBack) {
//                        IconButton(onClick = { appRouter.pop() }) {
//                            Text("←")
//                        }
//                    }
//                }
//            )
//        }
//    ) { paddingValues ->
//        Column(
//            modifier = Modifier
//                .fillMaxSize()
//                .padding(paddingValues)
//                .padding(16.dp)
//        ) {
//            Text(
//                text = "Birthday List",
//                style = MaterialTheme.typography.headlineMedium
//            )
//
//            Spacer(modifier = Modifier.height(16.dp))
//
//            if (birthDays.isEmpty()) {
//                Text("No birthdays to show")
//            } else {
//                LazyColumn(
//                    verticalArrangement = Arrangement.spacedBy(8.dp)
//                ) {
//                    items(birthDays) { birthday ->
//                        Card(
//                            modifier = Modifier.fillMaxWidth()
//                        ) {
//                            Column(
//                                modifier = Modifier.padding(16.dp)
//                            ) {
//                                Text(
//                                    text = birthday.name,
//                                    style = MaterialTheme.typography.titleMedium
//                                )
//                                Text("Date: ${birthday.date}")
//                                birthday.age?.let { age ->
//                                    Text("Age: $age")
//                                }
//                            }
//                        }
//                    }
//                }
//            }
//        }
//    }
//}
//
//@OptIn(ExperimentalMaterial3Api::class)
//@Composable
//fun NewsDetailScreen(args: NewsDetailScreenArgs) {
//    val appRouter = useAppRouter()
//
//    Scaffold(
//        topBar = {
//            TopAppBar(
//                title = { Text("News Detail") },
//                navigationIcon = {
//                    if (appRouter.canGoBack) {
//                        IconButton(onClick = { appRouter.pop() }) {
//                            Text("←")
//                        }
//                    }
//                }
//            )
//        }
//    ) { paddingValues ->
//        Column(
//            modifier = Modifier
//                .fillMaxSize()
//                .padding(paddingValues)
//                .padding(16.dp),
//            verticalArrangement = Arrangement.spacedBy(16.dp)
//        ) {
//            Text(
//                text = args.title,
//                style = MaterialTheme.typography.headlineMedium
//            )
//
//            Card(
//                modifier = Modifier.fillMaxWidth()
//            ) {
//                Column(
//                    modifier = Modifier.padding(16.dp),
//                    verticalArrangement = Arrangement.spacedBy(8.dp)
//                ) {
//                    Text("News ID: ${args.newsId}")
//                    args.author?.let { Text("Author: $it") }
//                    args.publishedDate?.let { Text("Published: $it") }
//                    args.imageUrl?.let { Text("Image URL: $it") }
//                }
//            }
//
//            Text(
//                text = args.content,
//                style = MaterialTheme.typography.bodyLarge
//            )
//        }
//    }
//}
