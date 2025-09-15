package com.datnx.my_auth.main

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.datnx.design_system.components.ButtonApp
import com.datnx.router.AuthRoute
import com.datnx.router.manager.useAppRouter



@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TestSplashScreen() {
    val appRouter = useAppRouter()
    val authState by AuthStateManager.authState.collectAsState()

    LaunchedEffect(authState) {
        Log.e("authState", "authState = $authState")
    }

    Scaffold(
        topBar = {
            TopAppBar(title = { Text("Platform screen") })
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
                text = "Welcome to My App",
                style = MaterialTheme.typography.headlineMedium
            )

            Spacer(modifier = Modifier.height(32.dp))

            Button(
                onClick = {
                    appRouter.navigate(AuthRoute.LOGIN_ROUTE)
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Go to Login")
            }
        }
    }
}