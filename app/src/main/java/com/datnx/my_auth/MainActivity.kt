package com.datnx.my_auth

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.MaterialTheme
import com.datnx.my_auth.main.MyAuthApp

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        GlobalUser.init(this)
        setContent {
            MaterialTheme {
                MyAuthApp()
            }
        }
    }
}
