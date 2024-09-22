package com.example.pouet

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.material3.adaptive.currentWindowAdaptiveInfo

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        val viewModel: MainViewModel by viewModels()
        setContent {

            val windowSize = currentWindowAdaptiveInfo().windowSizeClass

            if (viewModel.currentDestination.name == "PROFILE") {
                HomeScreen(windowSize, viewModel)
            } else {
                MyNavigationBar(windowSize, viewModel)
            }
        }

    }

}