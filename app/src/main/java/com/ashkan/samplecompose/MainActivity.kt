package com.ashkan.samplecompose

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.ashkan.samplecompose.ui.navigation.MainNavHost
import com.ashkan.samplecompose.ui.theme.SampleComposeTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()
        setContent {
            val navController = rememberNavController()
            //val navBackStackEntry by navController.currentBackStackEntryAsState()

            SampleComposeTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) {
                    MainNavHost(navController = navController)
                }
            }
        }
    }
}