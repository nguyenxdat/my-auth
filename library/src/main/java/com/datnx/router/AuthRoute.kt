package com.datnx.router

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.datnx.router.base.BaseRoute
import com.datnx.router.manager.useAppRouter
import com.datnx.design_system.components.ButtonApp
import kotlinx.coroutines.launch

/**
 * Auth Route Implementation
 * Handles authentication related screens
 */
class AuthRoute : BaseRoute() {
    override val prefix: String = PREFIX
    
    companion object {
        private const val PREFIX = "/auth"
        const val LOGIN_ROUTE = "$PREFIX/login"
        const val REGISTER_ROUTE = "$PREFIX/register"
        const val FORGOT_PASSWORD_ROUTE = "$PREFIX/forgot_password"
        const val RESET_PASSWORD_ROUTE = "$PREFIX/reset_password"
    }
    
    override fun registerRoute(navGraphBuilder: NavGraphBuilder) {
        navGraphBuilder.apply {
            composable(LOGIN_ROUTE) {
                LoginScreen()
            }
            
            composable(REGISTER_ROUTE) {
                RegisterScreen()
            }
            
            composable(FORGOT_PASSWORD_ROUTE) {
                ForgotPasswordScreen()
            }
            
            composable(RESET_PASSWORD_ROUTE) {
                ResetPasswordScreen()
            }
        }
    }
    
    override fun openScreen(
        navController: NavHostController,
        extraData: Map<String, Any>?
    ): Boolean {
        extraData?.let { data ->
            when (data["screen"]) {
                "login" -> {
                    navController.navigate(LOGIN_ROUTE)
                    return true
                }
                "register" -> {
                    navController.navigate(REGISTER_ROUTE)
                    return true
                }
                "forgot_password" -> {
                    navController.navigate(FORGOT_PASSWORD_ROUTE)
                    return true
                }
                "reset_password" -> {
                    navController.navigate(RESET_PASSWORD_ROUTE)
                    return true
                }
            }
        }
        return false
    }
    
    override fun getRoutePaths(): List<String> {
        return listOf(
            LOGIN_ROUTE,
            REGISTER_ROUTE,
            FORGOT_PASSWORD_ROUTE,
            RESET_PASSWORD_ROUTE
        )
    }
}

// MARK: - Auth Screen Composables

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginScreen() {
    val appRouter = useAppRouter()
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    val scope = rememberCoroutineScope()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Login") },
                navigationIcon = {
                    if (appRouter.canGoBack) {
                        IconButton(onClick = { appRouter.pop() }) {
                            Text("←")
                        }
                    }
                }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = "Welcome Back",
                style = MaterialTheme.typography.headlineMedium
            )
            
            Spacer(modifier = Modifier.height(32.dp))
            
            OutlinedTextField(
                value = email,
                onValueChange = { email = it },
                label = { Text("Email") },
                modifier = Modifier.fillMaxWidth()
            )
            
            Spacer(modifier = Modifier.height(16.dp))
            
            OutlinedTextField(
                value = password,
                onValueChange = { password = it },
                label = { Text("Password") },
                visualTransformation = PasswordVisualTransformation(),
                modifier = Modifier.fillMaxWidth()
            )
            
            Spacer(modifier = Modifier.height(24.dp))

            ButtonApp(btnTitle = "Login", onBtnClick = {
                scope.launch {
                    AuthStateManager.login(email, password)
                }

            })

            Spacer(modifier = Modifier.height(16.dp))
            
            Row(
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                ButtonApp(
                    btnTitle = "Register",
                    onBtnClick = {
                        appRouter.navigate(AuthRoute.REGISTER_ROUTE)
                    }
                )
                
                TextButton(
                    onClick = {
                        appRouter.navigate(AuthRoute.FORGOT_PASSWORD_ROUTE)
                    }
                ) {
                    Text("Forgot Password?")
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RegisterScreen() {
    val appRouter = useAppRouter()
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }
    var fullName by remember { mutableStateOf("") }
    
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Register") },
                navigationIcon = {
                    if (appRouter.canGoBack) {
                        IconButton(onClick = { appRouter.pop() }) {
                            Text("←")
                        }
                    }
                }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = "Create Account",
                style = MaterialTheme.typography.headlineMedium
            )
            
            Spacer(modifier = Modifier.height(32.dp))
            
            OutlinedTextField(
                value = fullName,
                onValueChange = { fullName = it },
                label = { Text("Full Name") },
                modifier = Modifier.fillMaxWidth()
            )
            
            Spacer(modifier = Modifier.height(16.dp))
            
            OutlinedTextField(
                value = email,
                onValueChange = { email = it },
                label = { Text("Email") },
                modifier = Modifier.fillMaxWidth()
            )
            
            Spacer(modifier = Modifier.height(16.dp))
            
            OutlinedTextField(
                value = password,
                onValueChange = { password = it },
                label = { Text("Password") },
                visualTransformation = PasswordVisualTransformation(),
                modifier = Modifier.fillMaxWidth()
            )
            
            Spacer(modifier = Modifier.height(16.dp))
            
            OutlinedTextField(
                value = confirmPassword,
                onValueChange = { confirmPassword = it },
                label = { Text("Confirm Password") },
                visualTransformation = PasswordVisualTransformation(),
                modifier = Modifier.fillMaxWidth()
            )
            
            Spacer(modifier = Modifier.height(24.dp))
            
            Button(
                onClick = {
                    // Simulate registration success
//                    appRouter.pushAndClearStack(PlatformRoute.Companion.MAIN_ROUTE)
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Register")
            }
            
            Spacer(modifier = Modifier.height(16.dp))
            
            TextButton(
                onClick = {
                    appRouter.navigate(AuthRoute.LOGIN_ROUTE)
                }
            ) {
                Text("Already have an account? Login")
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ForgotPasswordScreen() {
    val appRouter = useAppRouter()
    var email by remember { mutableStateOf("") }
    var isEmailSent by remember { mutableStateOf(false) }
    
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Forgot Password") },
                navigationIcon = {
                    if (appRouter.canGoBack) {
                        IconButton(onClick = { appRouter.pop() }) {
                            Text("←")
                        }
                    }
                }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            if (!isEmailSent) {
                Text(
                    text = "Reset Password",
                    style = MaterialTheme.typography.headlineMedium
                )
                
                Spacer(modifier = Modifier.height(16.dp))
                
                Text(
                    text = "Enter your email address and we'll send you a link to reset your password.",
                    style = MaterialTheme.typography.bodyMedium
                )
                
                Spacer(modifier = Modifier.height(32.dp))
                
                OutlinedTextField(
                    value = email,
                    onValueChange = { email = it },
                    label = { Text("Email") },
                    modifier = Modifier.fillMaxWidth()
                )
                
                Spacer(modifier = Modifier.height(24.dp))
                
                Button(
                    onClick = {
                        isEmailSent = true
                    },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Send Reset Link")
                }
            } else {
                Text(
                    text = "Email Sent!",
                    style = MaterialTheme.typography.headlineMedium
                )
                
                Spacer(modifier = Modifier.height(16.dp))
                
                Text(
                    text = "We've sent a password reset link to $email",
                    style = MaterialTheme.typography.bodyMedium
                )
                
                Spacer(modifier = Modifier.height(32.dp))
                
                Button(
                    onClick = {
                        appRouter.navigate(AuthRoute.LOGIN_ROUTE)
                    },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Back to Login")
                }
            }
            
            Spacer(modifier = Modifier.height(16.dp))
            
            TextButton(
                onClick = {
                    appRouter.navigate(AuthRoute.LOGIN_ROUTE)
                }
            ) {
                Text("Back to Login")
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ResetPasswordScreen() {
    val appRouter = useAppRouter()
    var newPassword by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }
    
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Reset Password") },
                navigationIcon = {
                    if (appRouter.canGoBack) {
                        IconButton(onClick = { appRouter.pop() }) {
                            Text("←")
                        }
                    }
                }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = "Create New Password",
                style = MaterialTheme.typography.headlineMedium
            )
            
            Spacer(modifier = Modifier.height(32.dp))
            
            OutlinedTextField(
                value = newPassword,
                onValueChange = { newPassword = it },
                label = { Text("New Password") },
                visualTransformation = PasswordVisualTransformation(),
                modifier = Modifier.fillMaxWidth()
            )
            
            Spacer(modifier = Modifier.height(16.dp))
            
            OutlinedTextField(
                value = confirmPassword,
                onValueChange = { confirmPassword = it },
                label = { Text("Confirm Password") },
                visualTransformation = PasswordVisualTransformation(),
                modifier = Modifier.fillMaxWidth()
            )
            
            Spacer(modifier = Modifier.height(24.dp))
            
            Button(
                onClick = {
                    // Simulate password reset success
                    appRouter.navigate(AuthRoute.LOGIN_ROUTE)
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Reset Password")
            }
        }
    }
}
